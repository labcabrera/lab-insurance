package org.lab.insurance.contract.creation.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan("org.lab.insurance.contract.creation.core")
@Import(ContractCreationIntegrationConfig.class)
public class ContractCreationCoreConfig {

}
