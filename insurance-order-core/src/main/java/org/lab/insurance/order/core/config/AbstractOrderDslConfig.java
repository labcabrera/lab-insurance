package org.lab.insurance.order.core.config;

import org.lab.insurance.common.integration.PayloadMongoAdapter;
import org.lab.insurance.common.integration.StateMachineProcesor;
import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.engine.core.config.EngineCoreIntegrationConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;

@Configuration
@ComponentScan({ "org.lab.insurance.order.core", "org.lab.insurance.engine.core.services" })
@Import(EngineCoreIntegrationConfig.class)
public abstract class AbstractOrderDslConfig {

	@Autowired
	protected Environment env;

	@Autowired
	protected ConnectionFactory connectionFactory;

	@Autowired
	protected AmqpTemplate amqpTemplate;

	@Autowired
	protected PayloadMongoAdapter<Order> orderMongoAdapter;

	@Autowired
	protected StateMachineProcesor<Order> stateMachineProcessor;

	@Bean
	JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

}
