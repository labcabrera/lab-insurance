package org.lab.insurance.contract.creation.core;

import org.lab.insurance.commons.InsuranceCommonConfig;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
// @EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
@IntegrationComponentScan
@EnableMongoRepositories("org.lab.insurance.domain")
@Import(InsuranceCommonConfig.class)
public class ContractCreationCoreApp {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ContractCreationCoreApp.class).web(false).run(args);
	}

	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
