package org.lab.insurance.portfolio.core.config.dsl;

import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.portfolio.core.processor.PortfolioInitializacionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;

@Configuration
public class PorfolioInitializationDslConfig extends AbstractDslConfig {

	@Autowired
	private PortfolioInitializacionProcessor initializationService;

	@Bean
	public IntegrationFlow initializacionFlow() {
		//@formatter:off
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, portfolioInitializationQueue())
				.errorChannel(portfolioInitializationChannel())
			)
			.log(Level.INFO, "Received portfolio initialization request")
			.transform(Transformers.fromJson(Contract.class))
			.handle(Contract.class, (request, headers) -> initializationService.initialize(request))
			.transform(Transformers.toJson(mapper()))
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow initializacionErrorFlow() {
		return IntegrationFlows
			.from(portfolioInitializationErrorChannel())
			.log(Level.ERROR, "Received portfolio initialization error")
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(env.getProperty("queues.portfolio.creation-error"))
			)
			.get();
	}
	//@formatter:on
}