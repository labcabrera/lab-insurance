package org.lab.insurance.order.core.config;

import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.order.core.processor.OrderValorizationProcessor;
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
public class OrderValorizationDslConfig extends AbstractOrderDslConfig {

	@Autowired
	private OrderValorizationProcessor valorizationProcessor;

	@Bean
	Queue valorizationQueue() {
		return new Queue(env.getProperty("queues.order.valorization"), true, false, false);
	}

	@Bean
	Queue valorizationErrorQueue() {
		return new Queue(env.getProperty("queues.order.valorization-error"), true, false, false);
	}

	@Bean
	MessageChannel valorizationErrorChannel() {
		return MessageChannels.direct().get();
	}

	@Bean
	MessageChannel portfolioAccountChannel() {
		return MessageChannels.direct().get();
	}

	//@formatter:off
	@Bean
	IntegrationFlow orderValorizationFlow() {
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, valorizationQueue())
				.errorChannel(valorizationErrorChannel())
			)
			.log(Level.INFO, "Received order valorization request")
			
			.transform(Transformers.fromJson(Order.class))
			.handle(Order.class, (request, headers) -> orderMongoAdapter.read(request.getId(), Order.class))
			.handle(Order.class, (request, headers) -> stateMachineProcessor.process(request, Order.States.VALUING.name(), false))
			.handle(Order.class, (request, headers) -> valorizationProcessor.process(request))
			.handle(Order.class, (request, headers) -> stateMachineProcessor.process(request, Order.States.VALUED.name(), false))
			.handle(Order.class, (request, headers) -> orderMongoAdapter.save(request))
			.publishSubscribeChannel(c -> c.applySequence(false)
			.subscribe(f -> f
					.channel(portfolioAccountChannel()))
			)
			.transform(Transformers.toJson(mapper()))
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow valorizationErrorFlow() {
		return IntegrationFlows
			.from(valorizationErrorChannel())
			.log(Level.ERROR, "Received order valorization error")
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(env.getProperty("queues.order.valorization-error"))
			)
			.get();
	}
	//@formatter:on

	@Bean
	IntegrationFlow portfolioAccountingtFlow() {
	//@formatter:off
		return IntegrationFlows
			.from(portfolioAccountChannel())
			.transform(Transformers.toJson(mapper()))
			.log(Level.INFO, "Sending portfolio accounting msg")
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(env.getProperty("queues.portfolio.order-account"))
			)
			.get();	
	}
	//@formatter:on

}
