package org.lab.insurance.ms.contract.creation.config;

import org.lab.insurance.model.config.InsuranceModelConfig;
import org.lab.insurance.ms.contract.creation.integration.IntegrationConfig;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@Import({ InsuranceModelConfig.class, IntegrationConfig.class })
@ComponentScan("org.lab.insurance.ms.contract.creation.service")
public class ContractCreationConfig {

}
