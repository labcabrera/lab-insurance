package org.lab.insurance.engine.processors.orders;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.exceptions.NoCotizationException;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.services.insurance.ValorizationService;

public class OrderValorizationProcessor implements Processor {

	@Inject
	private ValorizationService valorizationService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		try {
			valorizationService.valorizate(order);
		} catch (NoCotizationException ex) {
			// TODO controlar el flujo de los errores en camel
			throw ex;
		}
	}

}
