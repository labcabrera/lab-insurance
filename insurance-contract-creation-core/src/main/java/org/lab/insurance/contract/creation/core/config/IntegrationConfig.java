package org.lab.insurance.contract.creation.core.config;

import org.lab.insurance.contract.creation.core.domain.ContractCreationData;
import org.lab.insurance.contract.creation.core.domain.PaymentReceptionData;
import org.lab.insurance.contract.creation.core.integration.InitialPaymentTransformer;
import org.lab.insurance.contract.creation.core.integration.ReadContractTransformer;
import org.lab.insurance.contract.creation.core.service.ContractApprobationProcessor;
import org.lab.insurance.contract.creation.core.service.ContractCreationProcessor;
import org.lab.insurance.contract.creation.core.service.InitialPaymentReceptionProcessor;
import org.lab.insurance.domain.IntegrationConstants;
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
	private ContractCreationProcessor service;

	@Autowired
	private ContractApprobationProcessor approbationProcessor;

	@Autowired
	private InitialPaymentReceptionProcessor initialPaymentReceptionProcessor;

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Bean
	JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	@Bean
	Queue queueContractCreateRequest() {
		return new Queue(Queues.ContractCreation, true, false, false);
	}

	@Bean
	Queue queuePortfolioInitializacionRequest() {
		return new Queue(Queues.PortfolioInitialization, true, false, false);
	}

	@Bean
	Queue queueOrderInitializacionRequest() {
		return new Queue(Queues.OrderCreationRequest, true, false, false);
	}

	@Bean
	Queue queueContractCreateInitialDoc() {
		return new Queue(Queues.ContractInitialDocRequest, true, false, false);
	}

	@Bean
	Queue queueContractApprobation() {
		return new Queue(Queues.ContractApprobation, true, false, false);
	}

	@Bean
	Queue queueInitialPaymentReception() {
		return new Queue(Queues.InitialPaymentReception, true, false, false);
	}

	@Bean
	MessageChannel portfolioInitChannel() {
		return MessageChannels.direct().get();
	}

	@Bean
	MessageChannel orderInitChannel() {
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
			.transform(Transformers.fromJson(ContractCreationData.class, mapper()))
			.log(Level.INFO, "Received contract creation request")
			.handle(ContractCreationData.class, (request, headers) -> service.process(request))
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
			.transform(Transformers.fromJson(Contract.class, mapper()))
			.log(Level.INFO, "Received contract approbation request")
			.transform(readContractTransformer())
			.handle(Contract.class, (request, headers) -> approbationProcessor.process(request))
			.log(Level.INFO, "Contract approbed")
			.publishSubscribeChannel(c -> c.applySequence(false)
				.subscribe(f -> f
					.channel(portfolioInitChannel()))
				.subscribe(f -> f
					.channel(createContractDocumentation()))
			)
			.transform(Transformers.toJson(mapper()))
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow initialPaymentReceptionFlow() {
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, queueContractApprobation()))
			.transform(Transformers.fromJson(PaymentReceptionData.class, mapper()))
			.log(Level.INFO, "Received payment reception request")
			.handle(PaymentReceptionData.class, (request, headers) -> initialPaymentReceptionProcessor.process(request))
			.publishSubscribeChannel(c -> c.applySequence(false)
				.subscribe(f -> f
					.channel(orderInitChannel()))
			)
			.transform(Transformers.toJson(mapper()))
			.get();
	}
	//@formatter:on	

	//@formatter:off
	@Bean
	IntegrationFlow portfolioInitFlow() {
		return IntegrationFlows
			.from(portfolioInitChannel())
			.transform(Transformers.toJson(mapper()))
			.log("Sending portfolio initialization message")
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(IntegrationConstants.Queues.PortfolioInitialization)
			)
			.get();	
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow orderInitChannelFlow() {
		return IntegrationFlows
			.from(orderInitChannel())
			.log("Sending order initialization message")
			//TODO revisar la transformacion. Aunque solo sea a nivel de canal esta afectando a la salida
			.transform(new InitialPaymentTransformer())
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(IntegrationConstants.Queues.OrderCreationRequest)
			)
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow createContractDocumentationFlow() {
		return IntegrationFlows
			.from(createContractDocumentation())
			.log("Sending contract doc message")
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(IntegrationConstants.Queues.ContractInitialDocRequest)
			)
			.get();
	}
	//@formatter:on

}