package org.lab.insurance.bdd.contract.creation;

import org.lab.insurance.bdd.contract.config.BddConfig;
import org.lab.insurance.common.InsuranceCommonConfig;
import org.lab.insurance.contract.creation.core.config.ContractCreationCoreConfig;
import org.lab.insurance.contract.creation.core.config.ContractCreationIntegrationConfig;
import org.lab.insurance.engine.core.config.InsuranceEngineCoreConfig;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

import com.lab.insurance.contract.creation.gateway.config.IntegrationConfig;

@ContextConfiguration(loader = SpringBootContextLoader.class,
		classes = { IntegrationConfig.class, InsuranceCommonConfig.class, BddConfig.class,
				InsuranceEngineCoreConfig.class, InsuranceEngineCoreConfig.class,
				ContractCreationIntegrationConfig.class, ContractCreationCoreConfig.class })
public abstract class BddSupport {

}