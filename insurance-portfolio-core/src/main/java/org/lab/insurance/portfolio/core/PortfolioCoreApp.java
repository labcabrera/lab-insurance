package org.lab.insurance.portfolio.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories({ "org.lab.insurance.domain", "org.lab.insurance.portfolio.core.domain" })
public class PortfolioCoreApp {

	public static void main(String[] args) {
		SpringApplication.run(PortfolioCoreApp.class, args);
	}

}
