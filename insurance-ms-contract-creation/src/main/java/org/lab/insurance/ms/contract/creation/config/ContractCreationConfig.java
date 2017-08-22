package org.lab.insurance.ms.contract.creation.config;

import org.lab.insurance.model.config.InsuranceModelConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ InsuranceModelConfig.class })
@ComponentScan({ "org.lab.insurance.ms.contract.creation.service",
		"org.lab.insurance.ms.contract.creation.controllers" })
public class ContractCreationConfig {

}
