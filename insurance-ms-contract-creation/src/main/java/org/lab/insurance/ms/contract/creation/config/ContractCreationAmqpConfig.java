package org.lab.insurance.ms.contract.creation.config;

import org.lab.insurance.ms.contract.creation.amqp.ContractCreationListener;
import org.lab.insurance.ms.core.InsuranceCoreConstants.Queues;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractCreationAmqpConfig {

	@Bean
	public TopicExchange appExchange() {
		return new TopicExchange(Queues.TOPIC_EXCHANGE);
	}

	@Bean
	public Queue queueContractCreationIn() {
		return new Queue(Queues.CONTRACT_CREATION_IN, false);
	}

	@Bean
	public Queue queueContractCreationOut() {
		return new Queue(Queues.CONTRACT_CREATION_OUT, false);
	}

	@Bean
	public Binding bindingContractCreationQueueIn() {
		return BindingBuilder.bind(queueContractCreationIn()).to(appExchange()).with(Queues.CONTRACT_CREATION_IN);
	}

	@Bean
	public Binding bindingContractCreationQueueOut() {
		return BindingBuilder.bind(queueContractCreationOut()).to(appExchange()).with(Queues.CONTRACT_CREATION_OUT);
	}

	@Bean
	public SimpleMessageListenerContainer listenerContractCreationIn(ConnectionFactory connectionFactory,
			ContractCreationListener listener) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(Queues.CONTRACT_CREATION_IN);
		container.setMessageListener(listener);
		return container;
	}

	@Bean
	public ContractCreationListener contractCreationListener() {
		return new ContractCreationListener();
	}

}
