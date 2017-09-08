package org.lab.insurance.order.core.config;

import org.lab.insurance.domain.IntegrationConstants.Queues;
import org.lab.insurance.domain.insurance.Order;
import org.lab.insurance.order.core.integration.OrderMongoReader;
import org.lab.insurance.order.core.service.MarketOrderGeneratorProcessor;
import org.lab.insurance.order.core.service.OrderFeesProcessor;
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
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.messaging.MessageChannel;

@Configuration
public class OrderCoreIntegrationConfig {

	@Autowired
	private ConnectionFactory connectionFactory;

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Autowired
	private MarketOrderGeneratorProcessor marketOrderGeneratorProcessor;

	@Autowired
	private OrderMongoReader orderMongoReader;

	@Autowired
	private OrderFeesProcessor orderFeesProcessor;

	@Bean
	JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	@Bean
	Queue orderInitializationQueue() {
		return new Queue(Queues.OrderCreationRequest, true, false, false);
	}

	@Bean
	MessageChannel paymentCreationChannel() {
		return MessageChannels.direct().get();
	}

	//@formatter:off
	@Bean
	IntegrationFlow orderCreationFlow() {
		return IntegrationFlows //
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, orderInitializationQueue()))
			.log(Level.INFO, "Processing order initialization request")
			.transform(Transformers.fromJson(Order.class))
			.handle(Order.class, (request, headers) -> orderMongoReader.read(request))
			.handle(Order.class, (request, headers) -> orderFeesProcessor.process(request))
			.handle(Order.class, (request, headers) -> marketOrderGeneratorProcessor.process(request))
			.transform(Transformers.toJson(mapper()))
			//TODO
			.bridge(null)
			.get();
	}
	//@formatter:on
}