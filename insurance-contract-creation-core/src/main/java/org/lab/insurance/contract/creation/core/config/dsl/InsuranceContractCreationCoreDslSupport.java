package org.lab.insurance.contract.creation.core.config.dsl;

import org.lab.insurance.common.integration.dsl.InsuranceDslConfig;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.messaging.MessageChannel;

@Configuration
abstract class InsuranceContractCreationCoreDslSupport extends InsuranceDslConfig {

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
