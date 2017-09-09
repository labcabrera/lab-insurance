package com.lab.insurance.contract.creation.gateway.config;

import org.lab.insurance.domain.core.IntegrationConstants.Channels;
import org.lab.insurance.domain.core.IntegrationConstants.Queues;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.insurance.Order;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;

@Configuration
public class IntegrationConfig {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Bean
	JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	//@formatter:off
	@Bean
	IntegrationFlow creationflow() {
		return IntegrationFlows
			.from(MessageChannels.publishSubscribe(Channels.ContractCreationRequest))
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundGateway(amqpTemplate)
				.routingKey(Queues.ContractCreation)
			)
			.transform(Transformers.fromJson(Contract.class, mapper()))
			.channel(MessageChannels.direct(Channels.ContractCreationResponse))
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow approbationflow() {
		return IntegrationFlows
			.from(MessageChannels.publishSubscribe(Channels.ContractApprobationRequest))
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundGateway(amqpTemplate)
				.routingKey(Queues.ContractApprobation)
			)
			.transform(Transformers.fromJson(Contract.class, mapper()))
			.channel(MessageChannels.direct(Channels.ContractApprobationResponse))
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow initialPaymentflow() {
		return IntegrationFlows
			.from(MessageChannels.publishSubscribe(Channels.InitialPaymentReceptionRequest))
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundGateway(amqpTemplate)
				.routingKey(Queues.InitialPaymentReception)
			)
			.transform(Transformers.fromJson(Order.class, mapper()))
			.channel(MessageChannels.direct(Channels.InitialPaymentReceptionResponse))
			.get();
	}
	//@formatter:on
}