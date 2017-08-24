package org.lab.insurance.ms.contract.creation.integration;

import org.lab.insurance.ms.contract.creation.integration.IntegrationConstants.Channels;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.messaging.MessageChannel;

@Configuration
@IntegrationComponentScan("org.lab.insurance.ms.contract.creation.integration.gateway")
@ComponentScan("org.lab.insurance.ms.contract.creation.integration.endpoint")
@EnableIntegration
public class IntegrationConfig {

	@Bean(name = Channels.ContractCreationIn)
	public MessageChannel contractCreationInputChannel() {
		return new DirectChannel();
	}

	@Bean(name = Channels.ContractCreationProcess)
	public MessageChannel contractCreationProcessChannel() {
		return new DirectChannel();
	}

	@Bean(name = Channels.ContractCreationOut)
	public MessageChannel responseChannel() {
		return new PublishSubscribeChannel();
	}

	@Bean(name = Channels.ContractCreationError)
	public MessageChannel errorChannel() {
		return new PublishSubscribeChannel();
	}

}
