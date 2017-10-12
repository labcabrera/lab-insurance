package org.lab.insurance.order.core.config;

import org.lab.insurance.domain.action.contract.OrderValorization;
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

	//@formatter:off
	@Bean
	IntegrationFlow orderValorizationFlow() {
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, valorizationQueue())
				.errorChannel(valorizationErrorChannel())
				//TODO config
				.replyTimeout(600000)
			)
			.log(Level.INFO, "Received order valorization request")
			.transform(Transformers.fromJson(OrderValorization.class))
			.handle(OrderValorization.class, (request, headers) -> orderMongoAdapter.read(request.getOrderId(), Order.class))
			.handle(Order.class, (request, headers) -> stateMachineProcessor.process(request, Order.States.VALUING.name(), false))
			.handle(Order.class, (request, headers) -> valorizationProcessor.process(request))
			.handle(Order.class, (request, headers) -> stateMachineProcessor.process(request, Order.States.VALUED.name(), false))
			.handle(Order.class, (request, headers) -> orderMongoAdapter.save(request))
			.handle(Order.class, (request, headers) -> stateMachineProcessor.process(request, Order.States.ACCOUNTING.name(), false))
			
			//TODO gateway a la cola de portfolio-account-order y esperar al resultado
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundGateway(amqpTemplate)
				.routingKey(env.getProperty("queues.portfolio.order-account"))
			)
			
			.transform(Transformers.fromJson(OrderValorization.class))
			.handle(OrderValorization.class, (request, headers) -> orderMongoAdapter.read(request.getOrderId(), Order.class))
			.handle(Order.class, (request, headers) -> stateMachineProcessor.process(request, Order.States.ACCOUNTED.name(), false))
			.handle(Order.class, (request, headers) -> orderMongoAdapter.save(request))
			.transform(Transformers.toJson(mapper()))
			//TODO
			.bridge(null)
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

}
