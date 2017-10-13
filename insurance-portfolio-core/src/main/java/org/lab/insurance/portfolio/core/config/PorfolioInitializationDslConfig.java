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
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.messaging.MessageChannel;

@Configuration
@ComponentScan("org.lab.insurance.portfolio.core")
public class PorfolioInitializationDslConfig {

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
	public Queue portfolioInitializationQueue() {
		return new Queue(env.getProperty("queues.portfolio.creation"), true, false, false);
	}

	@Bean
	public Queue portfolioInitializationErrorQueue() {
		return new Queue(env.getProperty("queues.portfolio.creation-error"), true, false, false);
	}

	@Bean
	MessageChannel portfolioInitializationChannel() {
		return MessageChannels.direct().get();
	}

	//@formatter:off
	@Bean
	public IntegrationFlow initializacionFlow() {
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
			.from(portfolioInitializationChannel())
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