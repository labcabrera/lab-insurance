package org.lab.insurance.contract.creation.core.config.dsl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;
import org.springframework.messaging.MessageChannel;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
abstract class AbstractDslConfig {

	@Autowired
	protected Environment env;

	@Autowired
	protected ConnectionFactory connectionFactory;

	@Autowired
	protected AmqpTemplate amqpTemplate;

	@Autowired
	protected ObjectMapper objectMapper;

	@Bean
	JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper(objectMapper);
	}

	@Bean
	Queue contractCreateQueue() {
		return new Queue(env.getProperty("queues.contract.creation"), true, false, false);
	}

	@Bean
	Queue contractCreateErrorQueue() {
		return new Queue(env.getProperty("queues.contract.creation-error"), true, false, false);
	}

	@Bean
	Queue portfolioInitializacionQueue() {
		return new Queue(env.getProperty("queues.portfolio.creation"), true, false, false);
	}

	@Bean
	Queue orderInitializationQueue() {
		return new Queue(env.getProperty("queues.order.creation"), true, false, false);
	}

	@Bean
	Queue contractCreateDocQueue() {
		return new Queue(env.getProperty("queues.contract.doc-creation"), true, false, false);
	}

	@Bean
	Queue contractApprobationQueue() {
		return new Queue(env.getProperty("queues.contract.approbation"), true, false, false);
	}

	@Bean
	Queue initialPaymentReceptionQueue() {
		return new Queue(env.getProperty("queues.payment.initial-payment-reception"), true, false, false);
	}

	@Bean
	Queue initialPaymentReceptionErrorQueue() {
		return new Queue(env.getProperty("queues.payment.initial-payment-reception-error"), true, false, false);
	}

	@Bean
	MessageChannel contractCreationErrorChannel() {
		return MessageChannels.direct().get();
	}

	@Bean
	MessageChannel orderInitializationChannel() {
		return MessageChannels.direct().get();
	}

	@Bean
	MessageChannel initialPaymentReceptionErrorChannel() {
		return MessageChannels.direct().get();
	}

	@Bean
	MessageChannel portfolioInitializationChannel() {
		return MessageChannels.direct().get();
	}

	@Bean
	MessageChannel createContractDocumentationChannel() {
		return MessageChannels.direct().get();
	}
}
