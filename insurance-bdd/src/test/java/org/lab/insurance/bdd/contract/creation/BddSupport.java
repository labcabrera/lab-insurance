package org.lab.insurance.bdd.contract.creation;

import org.lab.insurance.bdd.contract.config.BddConfig;
import org.lab.insurance.common.InsuranceCommonConfig;
import org.lab.insurance.contract.creation.core.config.ContractCreationCoreConfig;
import org.lab.insurance.engine.core.config.InsuranceEngineCoreConfig;
import org.lab.insurance.order.core.config.OrderIntegrationConfig;
import org.lab.insurance.portfolio.core.config.PortfolioIntegrationConfig;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

import com.lab.insurance.contract.creation.gateway.config.IntegrationConfig;

//@formatter:off
@ContextConfiguration(
	loader = SpringBootContextLoader.class,
	classes = {
		BddConfig.class,
		IntegrationConfig.class,
		InsuranceCommonConfig.class,
		InsuranceEngineCoreConfig.class,
		InsuranceEngineCoreConfig.class,
		PortfolioIntegrationConfig.class,
		OrderIntegrationConfig.class,
		ContractCreationCoreConfig.class
	})
//@formatter:on
public abstract class BddSupport {

}