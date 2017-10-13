package org.lab.insurance.contract.creation.core.config;

import org.lab.insurance.common.integration.PayloadMongoAdapter;
import org.lab.insurance.common.integration.StateMachineProcesor;
import org.lab.insurance.contract.creation.core.service.InitialPaymentReceptionProcessor;
import org.lab.insurance.domain.action.contract.InitialPaymentReception;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.insurance.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;

@Configuration
public class InitialPaymentReceptionDslConfig extends AbstractDslConfig {

	@Autowired
	private InitialPaymentReceptionProcessor initialPaymentReceptionProcessor;

	@Autowired
	private PayloadMongoAdapter<Contract> contractMongoAdapter;

	@Autowired
	private PayloadMongoAdapter<Order> orderMongoAdapter;

	@Autowired
	private StateMachineProcesor<Contract> stateMachineProcessor;

	@Autowired
	private StateMachineProcesor<Order> orderStateMachineProcessor;

	//@formatter:off
	@Bean
	IntegrationFlow initialPaymentReceptionFlow() {
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, initialPaymentReceptionQueue())
				.errorChannel(initialPaymentReceptionErrorChannel())
			)
			.transform(Transformers.fromJson(InitialPaymentReception.class, mapper()))
			.log(Level.INFO, "Received intial payment reception request")
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
					.channel(orderInitializationChannel()))
			)
			.transform(Transformers.toJson(mapper()))
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow initialPaymentReceptionErrorFlow() {
		return IntegrationFlows
			.from(initialPaymentReceptionErrorChannel())
			.log(Level.ERROR, "Reveived initial payment reception error")
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(env.getProperty("queues.payment.initial-payment-reception-error"))
			)
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow orderInitChannelFlow() {
		return IntegrationFlows
			.from(orderInitializationChannel())
			.log("Sending order initialization message")
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(env.getProperty("queues.order.creation"))
			)
			.get();
	}
	//@formatter:on

}
