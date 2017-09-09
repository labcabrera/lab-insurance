package org.lab.insurance.engine.core.config;

import org.lab.insurance.engine.core.EngineConstants;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.amqp.Amqp;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.messaging.MessageChannel;

@Configuration
public class IntegrationConfig {

	@Autowired
	private AmqpTemplate amqpTemplate;

	@Bean(name = EngineConstants.Channels.ExecutionRequestSync)
	MessageChannel channelExecutionSyncRequest() {
		return MessageChannels.direct().get();
	}

	@Bean(name = EngineConstants.Channels.ExecutionRequestAsync)
	MessageChannel channelExecutionAsyncRequest() {
		return MessageChannels.direct().get();
	}

	//@formatter:off
	@Bean
	IntegrationFlow executionFlowAsync() {
		return IntegrationFlows
			.from(channelExecutionAsyncRequest())
			.transform(Transformers.toJson())
			.log(Level.INFO, "Processing execution reuqest")
			.handle(Amqp
				.outboundAdapter(amqpTemplate)
				.routingKeyExpression("headers.routingKey")
			)
			.get();
	}
	//@formatter:on

	//@formatter:off
	@Bean
	IntegrationFlow executionFlowSync() {
		return IntegrationFlows
			.from(channelExecutionAsyncRequest())
			.transform(Transformers.toJson())
			.log(Level.INFO, "Processing execution reuqest")
			.handle(Amqp
				.outboundGateway(amqpTemplate)
				.routingKeyExpression("headers.routingKey")
			)
			.get();
	}
	//@formatter:on

}
