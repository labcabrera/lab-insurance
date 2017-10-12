package org.lab.insurance.contract.creation.core.config.modules;

import org.lab.insurance.common.integration.PayloadMongoAdapter;
import org.lab.insurance.common.integration.StateMachineProcesor;
import org.lab.insurance.contract.creation.core.service.InitialPaymentReceptionProcessor;
import org.lab.insurance.domain.action.contract.InitialPaymentReception;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.insurance.Order;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.messaging.MessageChannel;

@Configuration
public class InitialPaymentReceptionConfig {

	@Autowired
	private Environment env;

	@Autowired
	private ConnectionFactory connectionFactory;

	@Autowired
	private InitialPaymentReceptionProcessor initialPaymentReceptionProcessor;

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Autowired
	private JsonObjectMapper<?, ?> mapper;

	@Autowired
	private PayloadMongoAdapter<Contract> contractMongoAdapter;

	@Autowired
	private PayloadMongoAdapter<Order> orderMongoAdapter;

	@Autowired
	private StateMachineProcesor<Contract> stateMachineProcessor;

	@Autowired
	private StateMachineProcesor<Order> orderStateMachineProcessor;

	@Value("${queues.payment.initial-payment-reception}")
	private String queueNamePaymentReception;

	@Bean
	Queue queueInitialPaymentReception() {
		return new Queue(queueNamePaymentReception, true, false, false);
	}

	@Bean
	MessageChannel orderInitChannel() {
		return MessageChannels.direct().get();
	}

	//@formatter:off
	@Bean
	IntegrationFlow initialPaymentReceptionFlow() {
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, queueInitialPaymentReception()))
			.transform(Transformers.fromJson(InitialPaymentReception.class, mapper))
			.log(Level.INFO, "Received payment reception")
			// Contract
			.handle(InitialPaymentReception.class, (request, headers) -> initialPaymentReceptionProcessor.process(request))
			.handle(Contract.class, (request, headers) -> stateMachineProcessor.process(request, Contract.States.STARTED.name(), true))
			.handle(Contract.class, (request, headers) -> contractMongoAdapter.save(request))
			// Order
			.handle(Contract.class, (request,headers) -> request.getOrders().iterator().next())
			.handle(Order.class, (request, headers) -> orderStateMachineProcessor.process(request, Order.States.INITIAL.name(), true))
			.handle(Order.class, (request, headers) -> orderMongoAdapter.save(request))
			
			.log(Level.INFO, "Processed initial payment")
			.publishSubscribeChannel(c -> c.applySequence(false)
				.subscribe(f -> f
					.channel(orderInitChannel()))
			)
			.transform(Transformers.toJson(mapper))
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow orderInitChannelFlow() {
		return IntegrationFlows
			.from(orderInitChannel())
			.log("Sending order initialization message")
			.transform(Transformers.toJson(mapper))
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(env.getProperty("queues.order.creation"))
			)
			.get();
	}
	//@formatter:on

}
