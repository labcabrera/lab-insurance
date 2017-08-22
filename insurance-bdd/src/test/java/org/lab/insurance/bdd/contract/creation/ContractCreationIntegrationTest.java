package org.lab.insurance.bdd.contract.creation;

import org.lab.insurance.bdd.contract.config.IntegrationTestConfig;
import org.lab.insurance.ms.contract.creation.config.ContractCreationConfig;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(loader = SpringBootContextLoader.class,
		classes = { ContractCreationConfig.class, IntegrationTestConfig.class })
public class ContractCreationIntegrationTest {

}