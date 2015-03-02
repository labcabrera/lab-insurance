package org.lab.insurance.engine.camel.routing;

import org.apache.camel.builder.RouteBuilder;
import org.lab.insurance.engine.processors.orders.MarketOrderGeneratorProcessor;
import org.lab.insurance.engine.processors.orders.OrderFeesProcessor;
import org.lab.insurance.engine.processors.orders.OrderPrepareProcessor;
import org.lab.insurance.engine.processors.orders.OrderProcessor;
import org.lab.insurance.engine.processors.orders.OrderValorizationProcessor;
import org.lab.insurance.engine.processors.orders.OrderValueDateProcessor;
import org.lab.insurance.engine.processors.orders.ValorizacionActionScheduler;

public class OrderRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		from("direct:order_process") //
				.bean(OrderPrepareProcessor.class) //
				.bean(OrderFeesProcessor.class) //
				.bean(OrderValueDateProcessor.class) //
				.bean(OrderProcessor.class) //
				.bean(MarketOrderGeneratorProcessor.class) //
				.bean(ValorizacionActionScheduler.class);

		from("direct:order_valorizarion") //
				.bean(OrderValorizationProcessor.class);
	}
}