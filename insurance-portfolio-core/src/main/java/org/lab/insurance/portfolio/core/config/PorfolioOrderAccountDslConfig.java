package org.lab.insurance.portfolio.core.config;

import org.lab.insurance.common.integration.PayloadMongoAdapter;
import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.portfolio.core.processor.OrderAccountProcessor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.integration.amqp.dsl.Amqp;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.integration.support.json.Jackson2JsonObjectMapper;
import org.springframework.integration.support.json.JsonObjectMapper;

@Configuration
@ComponentScan("org.lab.insurance.portfolio.core")
public class PorfolioOrderAccountDslConfig {

	@Autowired
	private Environment env;

	@Autowired
	private ConnectionFactory connectionFactory;

	@Autowired
	private OrderAccountProcessor orderAccountProcessor;

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Autowired
	protected PayloadMongoAdapter<Order> orderMongoAdapter;

	@Bean
	public JsonObjectMapper<?, ?> mapper() {
		return new Jackson2JsonObjectMapper();
	}

	@Bean
	public Queue porfolioOrderAccountQueue() {
		return new Queue(env.getProperty("queues.portfolio.order-account"), true, false, false);
	}

	//@formatter:off
	@Bean
	public IntegrationFlow orderAccountFlow() {
		return IntegrationFlows
			.from(Amqp
				.inboundGateway(connectionFactory, amqpTemplate, porfolioOrderAccountQueue()))
			.log(Level.INFO, "Processing order accounting request")
			.transform(Transformers.fromJson(Order.class))
			.handle(Order.class, (request, headers) -> orderMongoAdapter.read(request.getId(), Order.class))
			.handle(Order.class, (request, headers) -> orderAccountProcessor.process(request))
			.transform(Transformers.toJson(mapper()))
			.get();
	}
	//@formatter:on
}
