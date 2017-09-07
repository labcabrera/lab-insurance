package org.lab.insurance.order.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories({ "org.lab.insurance.domain" })
public class OrderCoreApp {

	public static void main(String[] args) {
		SpringApplication.run(OrderCoreApp.class, args);
	}

}
