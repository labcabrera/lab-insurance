package org.lab.insurance.portfolio.gateway.config;

import org.lab.insurance.domain.IntegrationConstants.Channels;
import org.lab.insurance.domain.IntegrationConstants.Queues;
import org.lab.insurance.domain.portfolio.ContractPortfolioRelation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.amqp.AmqpOutboundEndpointSpec;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;

@Configuration
public class IntegrationConfig {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Bean
	public JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	@Bean
	public Queue portfolioInitializationRequest() {
		return new Queue(Queues.PortfolioInitialization, false);
	}

	@Bean
	public IntegrationFlow flow() {
		AmqpOutboundEndpointSpec outbound = Amqp.outboundGateway(amqpTemplate)
				.routingKey(Queues.PortfolioInitialization);
		return IntegrationFlows.from(MessageChannels.publishSubscribe(Channels.PortfolioInitializationRequest)) //
				.transform(Transformers.toJson(mapper())) //
				.handle(outbound) //
				.transform(Transformers.fromJson(ContractPortfolioRelation.class, mapper())) //
				.channel(MessageChannels.direct(Channels.PortfolioInitializationResponse)) //
				.get();
	}
}