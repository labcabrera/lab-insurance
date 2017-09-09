package org.lab.insurance.bdd.contract.creation;

import org.lab.insurance.bdd.contract.config.BddConfig;
import org.lab.insurance.common.InsuranceCommonConfig;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

import com.lab.insurance.contract.creation.gateway.config.IntegrationConfig;

@ContextConfiguration(loader = SpringBootContextLoader.class,
		classes = { IntegrationConfig.class, InsuranceCommonConfig.class, BddConfig.class })
public abstract class BddSupport {

}