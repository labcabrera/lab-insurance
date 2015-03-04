package org.lab.insurance.engine.camel.routing;

import org.apache.camel.builder.RouteBuilder;
import org.lab.insurance.engine.processors.ContractMessageConverter;
import org.lab.insurance.engine.processors.policy.InitializeContractNumber;
import org.lab.insurance.engine.processors.policy.InitializeContractPortfolios;
import org.lab.insurance.engine.processors.policy.NewContractProcessor;

public class ContractRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		onException(Exception.class).bean(ContractMessageConverter.class);

		from("direct:new_policy_action") //
				.bean(InitializeContractNumber.class) //
				.bean(InitializeContractPortfolios.class) //
				.bean(NewContractProcessor.class) //
				.bean(ContractMessageConverter.class);
	}
}
