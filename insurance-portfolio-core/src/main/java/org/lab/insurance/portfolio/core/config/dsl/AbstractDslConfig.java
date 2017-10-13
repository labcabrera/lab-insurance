package org.lab.insurance.portfolio.core.config.dsl;

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

@Configuration
abstract class AbstractDslConfig {

	@Autowired
	protected Environment env;

	@Autowired
	protected ConnectionFactory connectionFactory;

	@Autowired
	protected AmqpTemplate amqpTemplate;

	@Bean
	JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	@Bean
	Queue portfolioInitializationQueue() {
		return new Queue(env.getProperty("queues.portfolio.creation"), true, false, false);
	}

	@Bean
	Queue portfolioInitializationErrorQueue() {
		return new Queue(env.getProperty("queues.portfolio.creation-error"), true, false, false);
	}

	@Bean
	Queue porfolioOrderAccountQueue() {
		return new Queue(env.getProperty("queues.portfolio.order-account"), true, false, false);
	}

	@Bean
	Queue porfolioOrderAccountErrorQueue() {
		return new Queue(env.getProperty("queues.portfolio.order-account.error"), true, false, false);
	}

	@Bean
	MessageChannel portfolioOrderErrorChannel() {
		return MessageChannels.direct().get();
	}

	@Bean
	MessageChannel portfolioInitializationChannel() {
		return MessageChannels.direct().get();
	}

	@Bean
	MessageChannel portfolioInitializationErrorChannel() {
		return MessageChannels.direct().get();
	}
}
