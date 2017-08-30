package org.lab.insurance.contract.creation.core.config;

import org.lab.insurance.contract.creation.core.domain.ContractCreationData;
import org.lab.insurance.contract.creation.core.service.ContractCreatedProcessor;
import org.lab.insurance.contract.creation.core.service.ContractCreationService;
import org.lab.insurance.domain.IntegrationConstants.Queues;
import org.lab.insurance.domain.contract.Contract;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.amqp.AmqpInboundGatewaySpec;
import org.springframework.integration.dsl.channel.MessageChannels;
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
	private ContractCreatedProcessor contractCreatedProcessor;
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

	@Bean
	public MessageChannel publishContractCreatedChannel() {
		return MessageChannels.direct("dummy-event-channel").get();
	}

	@Bean
	public IntegrationFlow mainFlow() {
		AmqpInboundGatewaySpec inboundGateway = Amqp.inboundGateway(connectionFactory, amqpTemplate,
				queueContractCreateRequest());
		return IntegrationFlows //
				.from(inboundGateway) //
				.transform(Transformers.fromJson(ContractCreationData.class, mapper())) //
				.log(Level.INFO) //
				.handle(ContractCreationData.class, (request, headers) -> service.process(request)) //
				.handle(Contract.class, (request, headers) -> contractCreatedProcessor.process(request)) //
				.transform(Transformers.toJson(mapper())) //
				// .channel(publishContractCreatedChannel()) //
				.get();
	}

	@Bean
	public IntegrationFlow publishContractCreation() {
		return IntegrationFlows.from(publishContractCreatedChannel()) //
				.transform(Transformers.toJson(mapper())) //
				// .handle(System.out::println) //
				.log(Level.INFO, "contract-creation-event") //
				// TODO publicar en rabbit en los diferentes modulos
				// .handle(Amqp.outboundGateway(amqpTemplate) //
				// .routingKey(Queues.PortfolioInitializationRequest)) //
				.bridge(null).get();

	}

}