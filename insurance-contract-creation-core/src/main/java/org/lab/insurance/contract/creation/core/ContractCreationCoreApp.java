package org.lab.insurance.contract.creation.core;

import org.lab.insurance.common.InsuranceCommonConfig;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;

@SpringBootApplication
@IntegrationComponentScan
@EnableMongoRepositories("org.lab.insurance.domain")
@Import(InsuranceCommonConfig.class)
public class ContractCreationCoreApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ContractCreationCoreApp.class).web(WebApplicationType.NONE).run(args);
	}

}
