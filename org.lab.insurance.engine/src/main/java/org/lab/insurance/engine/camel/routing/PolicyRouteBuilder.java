package org.lab.insurance.engine.camel.routing;

import org.apache.camel.builder.RouteBuilder;
import org.lab.insurance.engine.processors.PolicyMessageConverter;
import org.lab.insurance.engine.processors.policy.InitializePolicyNumber;
import org.lab.insurance.engine.processors.policy.InitializePolicyPortfolios;
import org.lab.insurance.engine.processors.policy.NewPolicyProcessor;

public class PolicyRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		onException(Exception.class).bean(PolicyMessageConverter.class);

		from("direct:new_policy_action") //
				.bean(InitializePolicyNumber.class) //
				.bean(InitializePolicyPortfolios.class) //
				.bean(NewPolicyProcessor.class) //
				.bean(PolicyMessageConverter.class);
	}
}
