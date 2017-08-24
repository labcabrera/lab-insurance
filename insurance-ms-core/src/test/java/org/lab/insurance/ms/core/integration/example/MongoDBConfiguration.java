package org.lab.insurance.ms.core.integration.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.handler.LoggingHandler.Level;
import org.springframework.messaging.MessageHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class MongoDBConfiguration {

//	@Bean
//	public MongoDbFactory mongoDbFactory() throws Exception {
//		return new SimpleMongoDbFactory(new MongoClient(), "lab-insurance");
//	}

	@Bean
	@ServiceActivator(inputChannel = DemoConstants.CHANNEL_STORE)
	public MessageHandler mongodbAdapter() throws Exception {
		log.info("mongodbAdapter()");
		return new LoggingHandler(Level.INFO);
//		MongoDbStoringMessageHandler adapter = new MongoDbStoringMessageHandler(mongoDbFactory());
//		adapter.setCollectionNameExpression(new LiteralExpression("demo-constants"));
//		return adapter;
	}
}
