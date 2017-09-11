package org.lab.insurance.order.gateway;

import org.apache.commons.lang3.StringUtils;
import org.lab.insurance.domain.hateoas.config.InsuranceHateoasConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableMongoRepositories("org.lab.insurance.domain")
@Import({ InsuranceHateoasConfig.class })
public class OrderGatewayApp {

	public static void main(String[] args) {
		SpringApplication.run(OrderGatewayApp.class, args);
	}

	@Bean
	public Docket petApi() {
		//@formatter:off
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
				.apis(RequestHandlerSelectors.basePackage("org.lab.insurance"))
				.paths(PathSelectors.any())
				.build()
			.tags(
				new Tag("order-resource-controller", StringUtils.EMPTY),
				new Tag("order-search-controller", StringUtils.EMPTY)
			);
		//@formatter:on
	}
}
