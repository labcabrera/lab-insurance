package org.lab.insurance.ms.contract.creation.config;

import org.lab.insurance.model.config.InsuranceModelConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration

//@EnableMongoRepositories("org.lab.insurance.model")

@Import(InsuranceModelConfig.class)

public class ContractCreationConfig {

}
