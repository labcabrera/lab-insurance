package org.lab.insurance.bdd.common;

import org.lab.insurance.domain.core.IntegrationConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitTestOperations {

	// @Autowired
	// private AmqpTemplate template;

	@Autowired
	Queue queuePortfolioCreateRequest() {
		return new Queue(IntegrationConstants.Queues.PortfolioInitialization);
	}

	public void waitUntilMessageIsEmpty(String key) {
	}

}
