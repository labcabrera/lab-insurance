package org.lab.insurance.contract.creation.core.config;

import org.lab.insurance.contract.creation.core.domain.ContractCreationData;
import org.lab.insurance.contract.creation.core.service.ContractCreationService;
import org.lab.insurance.domain.IntegrationConstants;
import org.lab.insurance.domain.IntegrationConstants.Queues;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.messaging.MessageChannel;

@Configuration
public class IntegrationConfig {

	@Autowired
	private ConnectionFactory connectionFactory;
	@Autowired
	private ContractCreationService service;
	@Autowired
	private AmqpTemplate amqpTemplate;

	@Bean
	public JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	@Bean
	public Queue queueContractCreateRequest() {
		return new Queue(Queues.ContractRequest, true);
	}

	@Bean
	public Queue queuePortfolioInitializacionRequest() {
		return new Queue(Queues.PortfolioInitializationRequest, true);
	}

	@Bean(name = IntegrationConstants.Channels.ContractCreatedSubscribeChannel)
	public MessageChannel publishContractCreatedChannel() {
		return new PublishSubscribeChannel();
	}

	@Bean
	public IntegrationFlow mainFlow() {
		return IntegrationFlows //
				.from(Amqp.inboundGateway(connectionFactory, amqpTemplate, queueContractCreateRequest())) //
				.transform(Transformers.fromJson(ContractCreationData.class, mapper())) //
				.log(Level.INFO) //
				.handle(ContractCreationData.class, (request, headers) -> service.process(request)) //
				.channel(publishContractCreatedChannel()) //
				.transform(Transformers.toJson(mapper())) //
				.get();
	}

}