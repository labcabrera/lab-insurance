package org.lab.insurance.portfolio.core.config;

import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.portfolio.core.processor.PortfolioInitializacionProcessor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;

@Configuration
@ComponentScan("org.lab.insurance.portfolio.core")
public class PortfolioIntegrationConfig {

	@Autowired
	private Environment env;

	@Autowired
	private ConnectionFactory connectionFactory;

	@Autowired
	private PortfolioInitializacionProcessor initializationService;

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Bean
	public JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	@Bean
	public Queue portfolioInitializationRequest() {
		return new Queue(env.getProperty("queues.portfolio.creation"), true, false, false);
	}

	//@formatter:off
	@Bean
	public IntegrationFlow portfolioInitializacionFlow() {
		return IntegrationFlows //
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, portfolioInitializationRequest()))
			.log(Level.INFO, "Processing portfolio initialization request")
			.transform(Transformers.fromJson(Contract.class))
			.handle(Contract.class, (request, headers) -> initializationService.initialize(request))
			.transform(Transformers.toJson(mapper()))
			.get();
	}
	//@formatter:on
}