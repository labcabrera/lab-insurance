package org.lab.insurance.asset.gateway;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
// @EnableEurekaClient
@EnableDiscoveryClient
@EnableMongoRepositories("org.lab.insurance.domain.core.insurance")
public class AssetGatewayApp {

	public static void main(String[] args) {
		SpringApplication.run(AssetGatewayApp.class, args);
	}

	@Bean
	Docket petApi() {
		//@formatter:off
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
				.apis(RequestHandlerSelectors.basePackage("org.lab.insurance"))
				.paths(PathSelectors.any())
				.build()
			.tags(
				new Tag("asset-resource-controller", StringUtils.EMPTY),
				new Tag("asset-search-controller", StringUtils.EMPTY)
			);
		//@formatter:on
	}
}
