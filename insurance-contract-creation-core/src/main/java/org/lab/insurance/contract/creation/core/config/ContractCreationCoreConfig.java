package org.lab.insurance.contract.creation.core.config;

import org.lab.insurance.contract.creation.core.config.dsl.ContractCreationDslConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan("org.lab.insurance.contract.creation.core")
@Import(ContractCreationDslConfig.class)
public class ContractCreationCoreConfig {

}
