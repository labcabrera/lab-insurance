package org.lab.insurance.contract.creation.core;

import org.lab.insurance.commons.InsuranceCommonConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
@EnableMongoRepositories("org.lab.insurance.domain")
@Import(InsuranceCommonConfig.class)
public class ContractCreationCoreApp {

	public static void main(String[] args) {
		SpringApplication.run(ContractCreationCoreApp.class, args);
	}
}
