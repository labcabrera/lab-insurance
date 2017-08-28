package org.lab.insurance.ms.core.integration.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.handler.DelayHandler;
import org.springframework.messaging.MessageHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class IntegrationHandlerConfiguration {

	@Bean
	@ServiceActivator(inputChannel = DemoConstants.CHANNEL_PROCESS)
	public MessageHandler wsOutboundGateway() {
		log.info("Demo invocation (invocation >> response)");
		DelayHandler handler = new DelayHandler("test");
		handler.setDefaultDelay(500L);
		handler.setOutputChannelName(DemoConstants.CHANNEL_RESPONSE);
		return handler;
	}

}
