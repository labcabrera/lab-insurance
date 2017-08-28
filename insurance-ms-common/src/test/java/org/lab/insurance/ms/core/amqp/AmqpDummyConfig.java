package org.lab.insurance.ms.core.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class AmqpDummyConfig {

	public final static String QUEUE_FOO = "lab-insurance-foo-queue-w";
	public final static String QUEUE_BAR = "lab-insurance-bar-queue-w";
	public final static String ROUTING_KEY = "lab-insurance-routing-key-w";
	public final static String TOPIC_EXCHANGE = "lab-insurance-topic-exchange-w";

	@Bean
	public TopicExchange appExchange() {
		return new TopicExchange(TOPIC_EXCHANGE);
	}

	@Bean
	public Queue queueFoo() {
		return new Queue(QUEUE_FOO, false);
	}

	// @Bean
	// public Queue queueBar() {
	// return new Queue(QUEUE_BAR, false);
	// }

	@Bean
	public Binding bindingFoo() {
		return BindingBuilder.bind(queueFoo()).to(appExchange()).with(QUEUE_FOO);
	}

	// @Bean
	// public Binding bindingBar() {
	// return BindingBuilder.bind(queueBar()).to(appExchange()).with(ROUTING_KEY);
	// }

	// -- producer

	// @Bean
	// public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
	// final RabbitTemplate template = new RabbitTemplate(connectionFactory);
	// template.setMessageConverter(new Jackson2JsonMessageConverter());
	// return template;
	// }

	@Bean
	public SimpleMessageListenerContainer listenerFoo(ConnectionFactory connectionFactory) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(QUEUE_FOO);
		container.setMessageListener(dummyListenerFoo());
		return container;
	}

	// @Bean
	// public SimpleMessageListenerContainer listenerBar(ConnectionFactory connectionFactory) {
	// SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
	// container.setConnectionFactory(connectionFactory);
	// container.setQueueNames(QUEUE_BAR);
	// container.setMessageListener(dummyListenerBar());
	// return container;
	// }

	@Bean
	public DummyMessageListenerFoo dummyListenerFoo() {
		return new DummyMessageListenerFoo();
	}

	// @Bean
	// public DummyMessageListenerBar dummyListenerBar() {
	// return new DummyMessageListenerBar();
	// }

}
