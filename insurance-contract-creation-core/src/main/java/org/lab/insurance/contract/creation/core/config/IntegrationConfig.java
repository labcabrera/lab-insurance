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
	private AmqpTemplate amqpTemplate;

	@Bean
	JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	@Bean
	Queue queueContractCreateRequest() {
		return new Queue(Queues.ContractRequest, true);
	}

	@Bean
	Queue queuePortfolioInitializacionRequest() {
		return new Queue(Queues.PortfolioInitializationRequest, true);
	}

	@Bean
	Queue queueOrderInitializacionRequest() {
		return new Queue(Queues.OrderCreationRequest, true);
	}

	@Bean(name = IntegrationConstants.Channels.ContractCreatedSubscribeChannel)
	MessageChannel publishContractCreatedChannel() {
		return new PublishSubscribeChannel();
	}

	@Bean
	MessageChannel portfolioInitChannel() {
		return MessageChannels.direct().get();
	}

	@Bean
	MessageChannel publishSubscribeChannel() {
		return MessageChannels.publishSubscribe().get();
	}

	@Bean
	MessageChannel orderInitChannel() {
		return MessageChannels.direct().get();
	}

	//@formatter:off
	@Bean
	public IntegrationFlow mainFlow() {
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, queueContractCreateRequest()))
			.transform(Transformers.fromJson(ContractCreationData.class, mapper())) //
			.log(Level.INFO, "Received contract creation request")
			.handle(ContractCreationData.class, (request, headers) -> service.process(request))
			.publishSubscribeChannel(c -> c.applySequence(false)
				.subscribe(f -> f
					.channel(portfolioInitChannel()))
				.subscribe(f -> f
					.channel(orderInitChannel()))
			)
			.transform(Transformers.toJson(mapper()))
			.get();
	}
	//@formatter:on

	@Bean
	IntegrationFlow publishChannel() {
		return IntegrationFlows.from(publishSubscribeChannel()).log("XXXXXXXXXXXXXXX").bridge(null).get();
	}

	//@formatter:off
	@Bean
	IntegrationFlow portfolioInitFlow() {
		return IntegrationFlows
			.from(portfolioInitChannel())
			.log("Sending portfolio initialization message")
//			.transform(Contract.class, s -> s.getId())
//			.transform(Transformers.toJson(mapper()))
//			.handle(Amqp
//				.outboundAdapter(amqpTemplate)
//				.routingKey(IntegrationConstants.Queues.PortfolioInitializationRequest)
//				.defaultDeliveryMode(MessageDeliveryMode.PERSISTENT)
//			)
			.bridge(null)
			.get();	
	}
	//@formatter:on

	/**
	 * Crea un mensaje con la orden a procesar a partir del contrato y la encola en rabbitmq.
	 * @return
	 */
	//@formatter:off
	@Bean
	IntegrationFlow orderInitChannelFlow() {
		return IntegrationFlows
			.from(orderInitChannel())
			.log("Sending order initialization message")
//			.transform(new InitialPaymentTransformer())
//			.transform(Transformers.toJson(mapper()))
//			.handle(Amqp
//				.outboundGateway(amqpTemplate)
//				.routingKey(IntegrationConstants.Queues.OrderCreationRequest)
//			)
			.bridge(null)
			.get();
	}
	//@formatter:on

}