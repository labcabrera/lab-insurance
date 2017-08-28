package org.lab.insurance.contract.common.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableMongoRepositories("org.lab.insurance.domain")
@EnableSwagger2
public class ContractCommonGatewayApp {

	public static void main(String[] args) {
		SpringApplication.run(ContractCommonGatewayApp.class, args);
	}

}
