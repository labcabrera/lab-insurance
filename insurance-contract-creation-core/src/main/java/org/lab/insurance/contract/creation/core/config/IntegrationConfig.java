package org.lab.insurance.contract.creation.core.config;

import org.lab.insurance.contract.creation.core.domain.ContractCreationData;
import org.lab.insurance.contract.creation.core.service.ContractCreationService;
import org.lab.insurance.domain.IntegrationConstants.Queues;
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
import org.springframework.integration.handler.LoggingHandler.Level;
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
		return new Queue(Queues.ContractRequest, true);
	}

	@Bean
	public Queue queuePortfolioInitializacionRequest() {
		return new Queue(Queues.PortfolioInitializationRequest, true);
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
				.transform(Transformers.toJson(mapper())) //
				// TODO en este punto tenemos que encolar los mensajes para que se activen todos
				// los submodulos
				// .publishSubscribeChannel(x -> x.subscribe(f -> f.channel(c ->
				// c.queue("portfolio-initializacion"))))
				.get();
	}
}