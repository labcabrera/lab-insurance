package org.lab.insurance.ms.core.integration.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;

@Configuration
@ComponentScan("org.lab.insurance.ms.core.integration.configuration.endpoint")
@IntegrationComponentScan("org.lab.insurance.ms.core.integration.configuration.gateway")
@EnableIntegration
public class InfrastructureConfiguration {

	@Bean(DemoConstants.CHANNEL_REQUEST)
	public MessageChannel requestChannel() {
		return new DirectChannel();
	}

	@Bean(DemoConstants.CHANNEL_PROCESS)
	public MessageChannel invocationChannel() {
		return new DirectChannel();
	}

	@Bean(DemoConstants.CHANNEL_RESPONSE)
	public MessageChannel responseChannel() {
		return new PublishSubscribeChannel();
	}

	@Bean(DemoConstants.CHANNEL_STORE)
	public MessageChannel storeChannel() {
		return new DirectChannel();
	}

}
