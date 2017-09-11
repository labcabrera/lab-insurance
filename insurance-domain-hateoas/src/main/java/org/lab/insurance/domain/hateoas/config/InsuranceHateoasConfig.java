package org.lab.insurance.domain.hateoas.config;

import org.lab.insurance.domain.hateoas.mapper.InsuranceHateoasMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ma.glasnost.orika.MapperFacade;

@Configuration
public class InsuranceHateoasConfig {

	@Bean
	MapperFacade mapperFacade() {
		return new InsuranceHateoasMapper();
	}

}
