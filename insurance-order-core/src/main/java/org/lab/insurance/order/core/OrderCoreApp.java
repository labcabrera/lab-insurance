package org.lab.insurance.order.core;

import org.lab.insurance.common.InsuranceCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories({ "org.lab.insurance.domain", "org.lab.insurance.engine.core.domain.repository" })
@Import({ InsuranceCommonConfig.class })
public class OrderCoreApp {

	public static void main(String[] args) {
		SpringApplication.run(OrderCoreApp.class, args);
	}

}
