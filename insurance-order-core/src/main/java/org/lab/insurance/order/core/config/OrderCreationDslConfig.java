package org.lab.insurance.order.core.config;

import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.order.core.processor.MarketOrderGeneratorProcessor;
import org.lab.insurance.order.core.processor.OrderFeesProcessor;
import org.lab.insurance.order.core.processor.OrderValorizationScheduler;
import org.lab.insurance.order.core.processor.ValueDateProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.messaging.MessageChannel;

@Configuration
public class OrderCreationDslConfig extends AbstractOrderDslConfig {

	@Autowired
	private MarketOrderGeneratorProcessor marketOrderGeneratorProcessor;

	@Autowired
	private OrderFeesProcessor orderFeesProcessor;

	@Autowired
	private OrderValorizationScheduler valorizationScheduler;

	@Autowired
	private ValueDateProcessor valueDateProcessor;

	@Bean
	Queue orderCreationQueue() {
		return new Queue(env.getProperty("queues.order.creation"), true, false, false);
	}

	@Bean
	MessageChannel paymentCreationChannel() {
		return MessageChannels.direct().get();
	}

	@Bean
	Queue orderCreationErrorQueue() {
		return new Queue(env.getProperty("queues.order.creation-error"), true, false, false);
	}

	@Bean
	MessageChannel orderCreationErrorChannel() {
		return MessageChannels.direct().get();
	}

	//@formatter:off
	@Bean
	IntegrationFlow orderCreationFlow() {
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, orderCreationQueue())
				.errorChannel(orderCreationErrorChannel())
			)
			.log(Level.INFO, "Reveived order creation request")
			.transform(Transformers.fromJson(Order.class))
			.handle(Order.class, (request, headers) -> orderMongoAdapter.read(request.getId(), Order.class))
			.handle(Order.class, (request, headers) -> stateMachineProcessor.process(request, Order.States.PROCESSING.name(), true))
			.handle(Order.class, (request, headers) -> orderFeesProcessor.process(request))
			.handle(Order.class, (request, headers) -> valueDateProcessor.process(request))
			.handle(Order.class, (request, headers) -> marketOrderGeneratorProcessor.process(request))
			.handle(Order.class, (request, headers) -> valorizationScheduler.process(request))
			.handle(Order.class, (request, headers) -> stateMachineProcessor.process(request, Order.States.PROCESSED.name(), true))
			.handle(Order.class, (request, headers) -> orderMongoAdapter.save(request))
			.transform(Transformers.toJson(mapper()))
			//TODO
			.bridge(null)
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow orderCreationErrorFlow() {
		return IntegrationFlows
			.from(orderCreationErrorChannel())
			.log(Level.ERROR, "Received order creation error")
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(env.getProperty("queues.order.creation-error"))
			)
			.get();
	}
	//@formatter:on
}