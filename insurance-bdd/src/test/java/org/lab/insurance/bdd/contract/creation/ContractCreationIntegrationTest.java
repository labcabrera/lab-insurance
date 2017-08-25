package org.lab.insurance.bdd.contract.creation;

import org.lab.insurance.bdd.contract.config.IntegrationTestConfig;
import org.lab.insurance.model.config.InsuranceModelConfig;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

import com.lab.insurance.contract.creation.gateway.config.IntegrationGatewayConfig;

@ContextConfiguration(loader = SpringBootContextLoader.class, classes = { InsuranceModelConfig.class,
		IntegrationGatewayConfig.class, IntegrationTestConfig.class })
public class ContractCreationIntegrationTest {

}