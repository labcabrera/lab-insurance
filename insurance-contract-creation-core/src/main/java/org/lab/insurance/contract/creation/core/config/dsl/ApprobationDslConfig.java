package org.lab.insurance.contract.creation.core.config.dsl;

import org.lab.insurance.contract.creation.core.service.ContractApprobationProcessor;
import org.lab.insurance.domain.action.contract.ContractApprobation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;

@Configuration
public class ApprobationDslConfig extends InsuranceContractCreationCoreDslSupport {

	@Autowired
	private ContractApprobationProcessor approbationProcessor;

	@Bean
	IntegrationFlow approbationFlow() {
		//@formatter:off
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

	@Bean
	IntegrationFlow portfolioInitFlow() {
		//@formatter:off
		return IntegrationFlows
			.from(portfolioInitializationChannel())
			.transform(Transformers.toJson(mapper()))
			.log(Level.INFO, "Sending portfolio initialization message")
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(env.getProperty("queues.portfolio.creation"))
			)
			.get();	
	}
	//@formatter:on

	@Bean
	IntegrationFlow createContractDocumentationFlow() {
		//@formatter:off
		return IntegrationFlows
			.from(createContractDocumentationChannel())
			.log(Level.INFO,"Sending contract doc message")
			.transform(Transformers.toJson(mapper()))
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKey(env.getProperty("queues.contract.doc-creation"))
			)
			.get();
	}
	//@formatter:on
}
