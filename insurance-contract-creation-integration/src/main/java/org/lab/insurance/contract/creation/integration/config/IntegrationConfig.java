package org.lab.insurance.contract.creation.integration.config;

import org.lab.insurance.contract.creation.integration.IntegrationConstants;
import org.lab.insurance.contract.creation.integration.domain.ContractCreationData;
import org.lab.insurance.contract.creation.integration.service.ContractCreationService;
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
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;

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
		return new Queue(IntegrationConstants.Queues.ContractRequest, false);
	}

	@Bean
	public IntegrationFlow flow() {

		AmqpInboundGatewaySpec inboundGateway = Amqp.inboundGateway(connectionFactory, amqpTemplate,
				queueContractCreateRequest());

		return IntegrationFlows //
				.from(inboundGateway) //
				.transform(Transformers.fromJson(ContractCreationData.class, mapper())) //
				.log() //
				// .filter(ContractCreationData.class, u -> u.getAgreementCode() != null)
				.handle(ContractCreationData.class, (request, headers) -> service.process(request)) //
				.transform(Transformers.toJson(mapper())) //
				.get();
	}
}