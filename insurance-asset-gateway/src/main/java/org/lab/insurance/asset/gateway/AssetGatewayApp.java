package org.lab.insurance.asset.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
@EnableMongoRepositories("org.lab.insurance.domain")
public class AssetGatewayApp {

	public static void main(String[] args) {
		SpringApplication.run(AssetGatewayApp.class, args);
	}

}
