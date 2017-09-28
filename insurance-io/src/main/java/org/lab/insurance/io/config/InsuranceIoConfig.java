package org.lab.insurance.io.config;

import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.io.service.ContractDocumentCreationService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class InsuranceIoConfig {

	@Autowired
	private Environment env;

	@Autowired
	private ConnectionFactory connectionFactory;

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ContractDocumentCreationService docService;

	@Bean
	JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper(objectMapper);
	}

	@Bean
	Queue queueContractDocCreateRequest() {
		return new Queue(env.getProperty("queues.contract.doc-creation"), true, false, false);
	}

	//@formatter:off
	@Bean
	IntegrationFlow creationFlow() {
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, queueContractDocCreateRequest()))
			.transform(Transformers.fromJson(Contract.class, mapper()))
			.log(Level.INFO, "Received contract doc creation request")
			.handle(Contract.class, (request, headers) -> docService.process(request))
			.log(Level.INFO, "Contract doc created")
			.transform(Transformers.toJson(mapper()))
			.get();
	}
	//@formatter:on

}
