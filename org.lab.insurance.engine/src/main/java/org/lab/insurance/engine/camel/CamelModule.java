package org.lab.insurance.engine.camel;

import org.apache.camel.guice.CamelModuleWithMatchingRoutes;
import org.lab.insurance.engine.camel.routing.AssetRouteBuilder;
import org.lab.insurance.engine.camel.routing.OrderRouteBuilder;
import org.lab.insurance.engine.camel.routing.ContractRouteBuilder;

public class CamelModule extends CamelModuleWithMatchingRoutes {

	@Override
	protected void configure() {
		super.configure();
		bind(ContractRouteBuilder.class);
		bind(AssetRouteBuilder.class);
		bind(OrderRouteBuilder.class);
	}
}
