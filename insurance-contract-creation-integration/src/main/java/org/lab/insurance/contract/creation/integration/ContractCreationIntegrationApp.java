package org.lab.insurance.contract.creation.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
@EnableMongoRepositories("org.lab.insurance.model")
public class ContractCreationIntegrationApp {

	public static void main(String[] args) {
		SpringApplication.run(ContractCreationIntegrationApp.class, args);
	}
}
