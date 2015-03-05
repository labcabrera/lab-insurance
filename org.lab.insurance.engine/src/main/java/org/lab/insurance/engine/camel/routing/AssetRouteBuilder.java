package org.lab.insurance.engine.camel.routing;

import org.apache.camel.builder.RouteBuilder;
import org.lab.insurance.engine.processors.prices.GuaranteePriceCreationPercentProcessor;
import org.lab.insurance.engine.processors.prices.GuaranteePriceCreationProcessor;
import org.lab.insurance.engine.processors.prices.GuaranteePriceCreationValidator;

public class AssetRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

		/**
		 * Endpoint que procesa la generacion de precios de un fondo garantizado a partir de un porcentaje en un rango dado.
		 */
		from("direct:guarantee_price_creation"). //
				bean(GuaranteePriceCreationValidator.class).//
				bean(GuaranteePriceCreationPercentProcessor.class).//
				bean(GuaranteePriceCreationProcessor.class);

	}

}
