package org.lab.insurance.engine.camel.routing;

import org.apache.camel.builder.RouteBuilder;
import org.lab.insurance.engine.processors.PolicyMessageConverter;
import org.lab.insurance.engine.processors.orders.MarketOrderGeneratorProcessor;
import org.lab.insurance.engine.processors.orders.OrderFeesProcessor;
import org.lab.insurance.engine.processors.orders.OrderPrepareProcessor;
import org.lab.insurance.engine.processors.orders.OrderProcessor;
import org.lab.insurance.engine.processors.orders.OrderValueDateProcessor;
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

		from("direct:order_process") //
				.bean(OrderPrepareProcessor.class) //
				.bean(OrderFeesProcessor.class) //
				.bean(OrderValueDateProcessor.class) //
				.bean(OrderProcessor.class) //
				.bean(MarketOrderGeneratorProcessor.class);

	}
}
