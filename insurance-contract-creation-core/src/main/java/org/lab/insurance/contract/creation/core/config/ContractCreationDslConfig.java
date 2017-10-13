package org.lab.insurance.contract.creation.core.config;

import org.lab.insurance.contract.creation.core.integration.ReadContractTransformer;
import org.lab.insurance.contract.creation.core.service.ContractApprobationProcessor;
import org.lab.insurance.contract.creation.core.service.ContractCreationProcessor;
import org.lab.insurance.domain.action.contract.ContractApprobation;
import org.lab.insurance.domain.action.contract.ContractCreation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;

@Configuration
public class ContractCreationDslConfig extends AbstractDslConfig {

	@Autowired
	private ContractCreationProcessor creationProcessor;

	@Autowired
	private ContractApprobationProcessor approbationProcessor;

	@Bean
	ReadContractTransformer readContractTransformer() {
		return new ReadContractTransformer();
	}

	/**
	 * Input flow definition for contract creation.
	 * 
	 * @return
	 */
	//@formatter:off
	@Bean
	IntegrationFlow contractCreationFlow() {
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, contractCreateQueue())
				.errorChannel(contractCreationErrorChannel())
			)
			.transform(Transformers.fromJson(ContractCreation.class, mapper()))
			.log(Level.INFO, "Received contract creation request")
			.handle(ContractCreation.class, (request, headers) -> creationProcessor.process(request))
			.log(Level.INFO, "Contract created")
			.transform(Transformers.toJson(mapper()))
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow contractCreationErrorFlow() {
		return IntegrationFlows
			.from(contractCreationErrorChannel())
			.log(Level.ERROR, "Reveived contract creation error")
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(env.getProperty("queues.contract.creation-error"))
			)
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow approbationFlow() {
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, contractApprobationQueue())
			)
			.transform(Transformers.fromJson(ContractApprobation.class, mapper()))
			.log(Level.INFO, "Received contract approbation request")
			.handle(ContractApprobation.class, (request, headers) -> approbationProcessor.process(request))
			.publishSubscribeChannel(c -> c.applySequence(false)
				.subscribe(f -> f
					.channel(portfolioInitializationChannel()))
				.subscribe(f -> f
					.channel(createContractDocumentationChannel()))
			)
			.transform(Transformers.toJson(mapper()))
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow portfolioInitFlow() {
		return IntegrationFlows
			.from(portfolioInitializationChannel())
			.transform(Transformers.toJson(mapper()))
			.log("Sending portfolio initialization message")
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(env.getProperty("queues.portfolio.creation"))
			)
			.get();	
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow createContractDocumentationFlow() {
		return IntegrationFlows
			.from(createContractDocumentationChannel())
			.log("Sending contract doc message")
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(env.getProperty("queues.contract.doc-creation"))
			)
			.get();
	}
	//@formatter:on

}