package org.lab.insurance.ms.core.integration.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Component;

/**
 * Ejemplo basico de enrutamiento usando spring-integration.
 */
@SpringBootApplication
public class IntegrationBasicExample {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(IntegrationBasicExample.class, args);
		ContractCreationGateway bean = ctx.getBean(ContractCreationGateway.class);
		String processed01 = bean.process("data");
		System.out.println("data 01: " + processed01);
		String processed02 = bean.process("php");
		System.out.println("data 02: " + processed02);
	}

	@Bean("in")
	public MessageChannel inChannel() {
		return new DirectChannel();
	}

	@Bean("out")
	public MessageChannel outChannel() {
		return new PublishSubscribeChannel();
	}

	@Bean("error")
	public MessageChannel errorChannel() {
		return new PublishSubscribeChannel();
	}

	@MessagingGateway(name = "gateway", defaultRequestChannel = "in")
	public interface ContractCreationGateway {
		public String process(String data);

	}

	@Component
	public class DemoRoute {

		@Router(inputChannel = "in")
		public String filter(Message<String> msg) {
			System.out.println("routing " + msg.getPayload());
			if ("php".equals(msg.getPayload())) {
				return "error";
			}
			return "out";
		}
	}

	@Component
	public class ActivatorOut {

		@ServiceActivator(inputChannel = "out")
		public String getResponse(Message<String> msg) {
			System.out.println("response handler");
			return "Message(" + msg + ")";
		}
	}

	@Component
	public class ActivatorError {

		@ServiceActivator(inputChannel = "error")
		public String getResponse(Message<String> msg) {
			System.out.println("response handler");
			return "Error(" + msg + ")";
		}
	}
}
