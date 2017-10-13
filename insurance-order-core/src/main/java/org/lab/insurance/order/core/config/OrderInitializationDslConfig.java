package org.lab.insurance.order.core.config;

import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.order.core.processor.MarketOrderGeneratorProcessor;
import org.lab.insurance.order.core.processor.OrderFeesProcessor;
import org.lab.insurance.order.core.processor.OrderValorizationScheduler;
import org.lab.insurance.order.core.processor.ValueDateProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;

@Configuration
public class OrderInitializationDslConfig extends AbstractOrderDslConfig {

	@Autowired
	private MarketOrderGeneratorProcessor marketOrderGeneratorProcessor;

	@Autowired
	private OrderFeesProcessor orderFeesProcessor;

	@Autowired
	private OrderValorizationScheduler valorizationScheduler;

	@Autowired
	private ValueDateProcessor valueDateProcessor;

	//@formatter:off
	@Bean
	IntegrationFlow orderInitializationFlow() {
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, orderCreationQueue())
				.errorChannel(orderCreationErrorChannel())
			)
			.log(Level.INFO, "Reveived order initialization request")
			.transform(Transformers.fromJson(Order.class))
			.handle(Order.class, (request, headers) -> orderMongoAdapter.read(request.getId(), Order.class))
			.handle(Order.class, (request, headers) -> stateMachineProcessor.process(request, Order.States.PROCESSING.name(), false))
			.handle(Order.class, (request, headers) -> orderFeesProcessor.process(request))
			.handle(Order.class, (request, headers) -> valueDateProcessor.process(request))
			.handle(Order.class, (request, headers) -> marketOrderGeneratorProcessor.process(request))
			.handle(Order.class, (request, headers) -> valorizationScheduler.process(request))
			.handle(Order.class, (request, headers) -> stateMachineProcessor.process(request, Order.States.PROCESSED.name(), false))
			.handle(Order.class, (request, headers) -> orderMongoAdapter.save(request))
			.transform(Transformers.toJson(mapper()))
			//TODO
			.bridge(null)
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow orderInitializationErrorFlow() {
		return IntegrationFlows
			.from(orderCreationErrorChannel())
			.log(Level.ERROR, "Reveived order initialization request")
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(env.getProperty("queues.order.creation-error"))
			)
			.get();
	}
	//@formatter:on
}