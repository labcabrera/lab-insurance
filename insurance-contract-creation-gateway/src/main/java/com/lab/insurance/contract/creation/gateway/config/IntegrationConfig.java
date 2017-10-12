package com.lab.insurance.contract.creation.gateway.config;

import org.lab.insurance.domain.core.IntegrationConstants.Channels;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.insurance.Order;
import org.springframework.amqp.core.AmqpTemplate;
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
	JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	//@formatter:off
	@Bean
	IntegrationFlow creationflow() {
		String to = env.getProperty("queues.contract.creation");
		return IntegrationFlows
			.from(MessageChannels.publishSubscribe(Channels.ContractCreationRequest))
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundGateway(amqpTemplate)
				.routingKey(to)
			)
			.transform(Transformers.fromJson(Contract.class, mapper()))
			.channel(MessageChannels.direct(Channels.ContractCreationResponse))
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow approbationflow() {
		String to = env.getProperty("queues.contract.approbation");
		return IntegrationFlows
			.from(MessageChannels.publishSubscribe(Channels.ContractApprobationRequest))
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundGateway(amqpTemplate)
				.routingKey(to)
			)
			.transform(Transformers.fromJson(Contract.class, mapper()))
			.channel(MessageChannels.direct(Channels.ContractApprobationResponse))
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow initialPaymentflow() {
		String to = env.getProperty("queues.payment.initial-payment-reception");
		return IntegrationFlows
			.from(MessageChannels.publishSubscribe(Channels.InitialPaymentReceptionRequest))
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundGateway(amqpTemplate)
				.routingKey(to)
			)
			.transform(Transformers.fromJson(Order.class, mapper()))
			.channel(MessageChannels.direct(Channels.InitialPaymentReceptionResponse))
			.get();
	}
	//@formatter:on
}