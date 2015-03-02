package org.lab.insurance.engine.camel.routing;

import org.apache.camel.builder.RouteBuilder;
import org.lab.insurance.engine.processors.PolicyMessageConverter;
import org.lab.insurance.engine.processors.policy.NewPolicyProcessor;
import org.lab.insurance.engine.processors.policy.PolicyNumberProcessor;

public class PolicyRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		onException(Exception.class).bean(PolicyMessageConverter.class);

		from("direct:new_policy_action") //
				.bean(PolicyNumberProcessor.class) //
				.bean(NewPolicyProcessor.class) //
				.bean(PolicyMessageConverter.class);

	}
}
