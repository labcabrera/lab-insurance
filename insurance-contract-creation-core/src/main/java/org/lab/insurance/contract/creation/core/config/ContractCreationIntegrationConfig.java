package org.lab.insurance.contract.creation.core.config;

import org.lab.insurance.contract.creation.core.integration.ReadContractTransformer;
import org.lab.insurance.contract.creation.core.service.ContractApprobationProcessor;
import org.lab.insurance.contract.creation.core.service.ContractCreationProcessor;
import org.lab.insurance.domain.action.contract.ContractApprobation;
import org.lab.insurance.domain.action.contract.ContractCreation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class ContractCreationIntegrationConfig {

	@Autowired
	private Environment env;

	@Autowired
	private ConnectionFactory connectionFactory;

	@Autowired
	private ContractCreationProcessor service;

	@Autowired
	private ContractApprobationProcessor approbationProcessor;

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Bean
	JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper(objectMapper);
	}

	@Bean
	Queue queueContractCreateRequest() {
		return new Queue(env.getProperty("queues.contract.creation"), true, false, false);
	}

	@Bean
	Queue queuePortfolioInitializacionRequest() {
		return new Queue(env.getProperty("queues.portfolio.creation"), true, false, false);
	}

	@Bean
	Queue queueOrderInitializacionRequest() {
		return new Queue(env.getProperty("queues.order.creation"), true, false, false);
	}

	@Bean
	Queue queueContractCreateInitialDoc() {
		return new Queue(env.getProperty("queues.contract.doc-creation"), true, false, false);
	}

	@Bean
	Queue queueContractApprobation() {
		return new Queue(env.getProperty("queues.contract.approbation"), true, false, false);
	}

	@Bean
	MessageChannel portfolioInitChannel() {
		return MessageChannels.direct().get();
	}

	@Bean
	MessageChannel createContractDocumentation() {
		return MessageChannels.direct().get();
	}

	@Bean
	ReadContractTransformer readContractTransformer() {
		return new ReadContractTransformer();
	}

	//@formatter:off
	@Bean
	IntegrationFlow creationFlow() {
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, queueContractCreateRequest()))
			.transform(Transformers.fromJson(ContractCreation.class, mapper()))
			.log(Level.INFO, "Received contract creation request")
			.handle(ContractCreation.class, (request, headers) -> service.process(request))
			.log(Level.INFO, "Contract created")
			.transform(Transformers.toJson(mapper()))
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow approbationFlow() {
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, queueContractApprobation()))
			.transform(Transformers.fromJson(ContractApprobation.class, mapper()))
			.log(Level.INFO, "Received contract approbation request")
			.handle(ContractApprobation.class, (request, headers) -> approbationProcessor.process(request))
			.log(Level.INFO, "Contract approbed")
			.publishSubscribeChannel(c -> c.applySequence(false)
				.subscribe(f -> f
					.channel(portfolioInitChannel()))
				//TODO mover a logica del AM
				.subscribe(f -> f
					.channel(createContractDocumentation()))
			)
			.transform(Transformers.toJson(mapper()))
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow portfolioInitFlow() {
		String to = env.getProperty("queues.portfolio.creation");
		return IntegrationFlows
			.from(portfolioInitChannel())
			.transform(Transformers.toJson(mapper()))
			.log("Sending portfolio initialization message")
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(to)
			)
			.get();	
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow createContractDocumentationFlow() {
		String to = env.getProperty("queues.contract.doc-creation");
		return IntegrationFlows
			.from(createContractDocumentation())
			.log("Sending contract doc message")
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(to)
			)
			.get();
	}
	//@formatter:on

}