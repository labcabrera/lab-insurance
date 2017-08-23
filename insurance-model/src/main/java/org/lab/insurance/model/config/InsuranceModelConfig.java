package org.lab.insurance.model.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@ConditionalOnMissingBean(InsuranceModelConfig.class)
@Configuration
//@EnableAutoConfiguration
@EnableMongoRepositories("org.lab.insurance.model")
public class InsuranceModelConfig {

}
