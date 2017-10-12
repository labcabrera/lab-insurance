package org.lab.insurance.order.core.config;

import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.order.core.service.OrderValorizationProcessor;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;

@Configuration
public class OrderValorizationDslConfig extends AbstractOrderDslConfig {

	@Autowired
	private OrderValorizationProcessor valorizationProcessor;

	@Bean
	Queue orderValorizationQueue() {
		return new Queue(env.getProperty("queues.order.valorization"), true, false, false);
	}

	//@formatter:off
	@Bean
	IntegrationFlow orderCreationFlow() {
		return IntegrationFlows //
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, orderValorizationQueue()))
			.log(Level.INFO, "Processing order request")
			.transform(Transformers.fromJson(Order.class))
			.handle(Order.class, (request, headers) -> orderMongoAdapter.read(request.getId(), Order.class))
			.handle(Order.class, (request, headers) -> stateMachineProcessor.process(request, Order.States.VALUING.name(), true))
			.handle(Order.class, (request, headers) -> valorizationProcessor.process(request))
			.handle(Order.class, (request, headers) -> valorizationProcessor.process(request))
			//TODO
			.handle(Order.class, (request, headers) -> stateMachineProcessor.process(request, Order.States.PROCESSED.name(), true))
			.handle(Order.class, (request, headers) -> orderMongoAdapter.save(request))
			.transform(Transformers.toJson(mapper()))
			//TODO
			.bridge(null)
			.get();
	}
	//@formatter:on

}
