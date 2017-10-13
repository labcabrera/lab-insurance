package org.lab.insurance.bdd.contract.creation;

import org.lab.insurance.bdd.contract.config.BddConfig;
import org.lab.insurance.common.InsuranceCommonConfig;
import org.lab.insurance.contract.creation.core.config.ContractCreationCoreConfig;
import org.lab.insurance.engine.core.config.InsuranceEngineCoreConfig;
import org.lab.insurance.order.core.config.OrderInitializationDslConfig;
import org.lab.insurance.order.core.config.OrderValorizationDslConfig;
import org.lab.insurance.portfolio.core.config.dsl.PorfolioInitializationDslConfig;
import org.lab.insurance.portfolio.core.config.dsl.PorfolioOrderAccountDslConfig;
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
		PorfolioInitializationDslConfig.class,
		PorfolioOrderAccountDslConfig.class,
		OrderInitializationDslConfig.class,
		OrderValorizationDslConfig.class,
		ContractCreationCoreConfig.class
	})
//@formatter:on
public abstract class BddSupport {

}