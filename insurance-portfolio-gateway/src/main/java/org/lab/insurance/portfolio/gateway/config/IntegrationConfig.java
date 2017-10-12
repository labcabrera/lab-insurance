package org.lab.insurance.portfolio.gateway.config;

import org.lab.insurance.domain.core.IntegrationConstants.Channels;
import org.lab.insurance.domain.core.portfolio.ContractPortfolioRelation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;

@Configuration
public class IntegrationConfig {

	@Autowired
	private Environment env;

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Bean
	public JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	@Bean
	public Queue portfolioInitializationRequest() {
		return new Queue(env.getProperty("queues.portfolio.creation"), false);
	}

	//@formatter:off
	@Bean
	public IntegrationFlow flow() {
		String to = env.getProperty("queues.portfolio.creation");
		return IntegrationFlows
			.from(MessageChannels.publishSubscribe(Channels.PortfolioInitializationRequest))
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundGateway(amqpTemplate)
				.routingKey(to))
			.transform(Transformers.fromJson(ContractPortfolioRelation.class, mapper()))
			.channel(MessageChannels.direct(Channels.PortfolioInitializationResponse))
			.get();
	}
	//@formatter:on
}