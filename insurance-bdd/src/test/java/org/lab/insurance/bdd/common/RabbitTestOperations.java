package org.lab.insurance.bdd.common;

import java.util.Properties;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RabbitTestOperations {

	@Autowired
	private ConnectionFactory connectionFactory;

	@Bean
	RabbitAdmin rabbitAdmin() {
		return new RabbitAdmin(connectionFactory);
	}

	// @Autowired
	// Queue queuePortfolioCreateRequest() {
	// return new Queue(IntegrationConstants.Queues.PortfolioInitialization);
	// }

	public void waitUntilQueueIsEmpty(String queueName) {
		RabbitAdmin rabbitAdmin = rabbitAdmin();
		Properties properties;
		int count;
		long t0 = System.currentTimeMillis();
		long max = t0 + 1000 * 60 * 2;
		long t = System.currentTimeMillis();
		while (t < max) {
			properties = rabbitAdmin.getQueueProperties(queueName);
			count = Integer.parseInt(properties.get("QUEUE_MESSAGE_COUNT").toString());
			if (count == 0) {
				break;
			}
			try {
				Thread.sleep(500L);
			}
			catch (InterruptedException ignore) {
			}
			t = System.currentTimeMillis();
		}
	}

	public void purgue(String queueName) {
		rabbitAdmin().purgeQueue(queueName, false);
	}

}
