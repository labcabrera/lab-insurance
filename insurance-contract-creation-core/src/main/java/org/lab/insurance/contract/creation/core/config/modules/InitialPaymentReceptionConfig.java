package org.lab.insurance.contract.creation.core.config.modules;

import org.lab.insurance.contract.creation.core.service.InitialPaymentReceptionProcessor;
import org.lab.insurance.domain.action.contract.InitialPaymentReception;
import org.lab.insurance.domain.core.IntegrationConstants;
import org.lab.insurance.domain.core.IntegrationConstants.Queues;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.messaging.MessageChannel;

@Configuration
public class InitialPaymentReceptionConfig {

	@Autowired
	private ConnectionFactory connectionFactory;

	@Autowired
	private InitialPaymentReceptionProcessor initialPaymentReceptionProcessor;

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Autowired
	private JsonObjectMapper<?, ?> mapper;

	@Bean
	Queue queueInitialPaymentReception() {
		return new Queue(Queues.InitialPaymentReception, true, false, false);
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
			.log(Level.INFO, "Received payment reception request")
			.handle(InitialPaymentReception.class, (request, headers) -> initialPaymentReceptionProcessor.process(request))
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
				.routingKey(IntegrationConstants.Queues.OrderCreationRequest)
			)
			.get();
	}
	//@formatter:on

}
