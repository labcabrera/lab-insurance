package org.lab.insurance.common.integration.dsl;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public abstract class InsuranceDslConfig {

	@Autowired
	protected Environment env;

	@Autowired
	protected ConnectionFactory connectionFactory;

	@Autowired
	protected AmqpTemplate amqpTemplate;

	@Autowired
	protected ObjectMapper objectMapper;

	@Bean
	protected JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper(objectMapper);
	}

}
