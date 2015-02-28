package org.lab.insurance.engine.camel;

import org.apache.camel.guice.CamelModuleWithMatchingRoutes;
import org.lab.insurance.engine.camel.routing.AssetRouteBuilder;
import org.lab.insurance.engine.camel.routing.PolicyRouteBuilder;

public class CamelModule extends CamelModuleWithMatchingRoutes {

	@Override
	protected void configure() {
		super.configure();
		bind(PolicyRouteBuilder.class);
		bind(AssetRouteBuilder.class);
	}
}
