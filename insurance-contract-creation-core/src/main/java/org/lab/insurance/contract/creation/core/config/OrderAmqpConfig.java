package org.lab.insurance.contract.creation.core.config;

import org.lab.insurance.domain.IntegrationConstants;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.messaging.MessageChannel;

@Configuration
public class OrderAmqpConfig {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Qualifier(IntegrationConstants.Channels.ContractCreatedSubscribeChannel)
	@Autowired
	private MessageChannel publishSubscribeContractCreatedChannel;

	@Bean
	public IntegrationFlow publishOrderInitialization() {
		return IntegrationFlows //
				.from(publishSubscribeContractCreatedChannel) //
				// .transform(new ObjectToJsonTransformer()) //
				.handle(Amqp.outboundAdapter(amqpTemplate).routingKey(IntegrationConstants.Queues.OrderCreationRequest)
						.defaultDeliveryMode(MessageDeliveryMode.PERSISTENT))
				.get();
	}

}
