package org.lab.insurance.portfolio.core.config.dsl;

import org.lab.insurance.common.integration.PayloadMongoAdapter;
import org.lab.insurance.common.integration.StateMachineProcesor;
import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.portfolio.core.processor.PortfolioOrderProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;

@Configuration
@ComponentScan("org.lab.insurance.portfolio.core")
public class PorfolioOrderAccountDslConfig extends AbstractDslConfig {

	@Autowired
	private PortfolioOrderProcessor orderAccountProcessor;

	@Autowired
	protected PayloadMongoAdapter<Order> orderMongoAdapter;

	@Autowired
	protected StateMachineProcesor<Order> stateMachineProcessor;

	@Bean
	public IntegrationFlow orderAccountFlow() {
		//@formatter:off
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, porfolioOrderAccountQueue())
				.errorChannel(portfolioOrderErrorChannel())
			)
			.log(Level.INFO, "Received order accounting request")
			.transform(Transformers.fromJson(Order.class))
			.handle(Order.class, (request, headers) -> orderMongoAdapter.read(request.getId(), Order.class))
			.handle(Order.class, (request, headers) -> stateMachineProcessor.process(request, Order.States.ACCOUNTING.name(), false))
			.handle(Order.class, (request, headers) -> orderAccountProcessor.process(request))
			.handle(Order.class, (request, headers) -> stateMachineProcessor.process(request, Order.States.ACCOUNTED.name(), false))
			.handle(Order.class, (request, headers) -> orderMongoAdapter.save(request))
			.transform(Transformers.toJson(mapper()))
			.get();
	}
	//@formatter:on

	@Bean
	IntegrationFlow orderAccountFlowError() {
		//@formatter:off
		return IntegrationFlows
			.from(portfolioOrderErrorChannel())
			.log(Level.ERROR, "Received order accounting error")
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(env.getProperty("queues.portfolio.order-account.error"))
			)
			.get();
	}
	//@formatter:on
}
