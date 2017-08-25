package org.lab.insurance.ms.core.integration.dsl.amqp;

import java.io.IOException;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;

@Configuration
@EnableIntegration
@SpringBootApplication
public class ExampleDslAmqpApp {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ExampleDslAmqpApp.class, args);
		System.in.read();
	}

	@Bean
	public Queue queue() {
		return new Queue("dummy-queue", true, false, false);
	}

	@Bean
	public IntegrationFlow amqpFlow(ConnectionFactory connectionFactory) {
		return IntegrationFlows //
				.from(Amqp.inboundGateway(connectionFactory, queue())) //
				.transform("hello "::concat) //
				.transform(String.class, String::toUpperCase) //
				.get();
	}

	@Bean
	public IntegrationFlow amqpOutboundFlow(AmqpTemplate amqpTemplate) {
		return IntegrationFlows //
				.from("amqpOutboundInput") //
				.handle(Amqp.outboundAdapter(amqpTemplate).routingKeyExpression("headers.routingKey")) //
				.get();
	}
}