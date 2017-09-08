package org.lab.insurance.order.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableMongoRepositories("org.lab.insurance.domain")
public class OrderGatewayApp {

	public static void main(String[] args) {
		SpringApplication.run(OrderGatewayApp.class, args);
	}
}
