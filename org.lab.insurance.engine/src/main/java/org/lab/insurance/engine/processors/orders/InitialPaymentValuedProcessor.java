package org.lab.insurance.engine.processors.orders;

import javax.inject.Inject;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.ProducerTemplate;
import org.lab.insurance.model.jpa.contract.Contract;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.model.jpa.insurance.OrderType;
import org.lab.insurance.model.matchers.OrderTypeMatcher;

import ch.lambdaj.Lambda;

/**
 * Procesador que se ejecuta cuando se valoriza un pago inicial.
 */
public class InitialPaymentValuedProcessor implements Processor {

	@Inject
	private CamelContext camelContext;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		Contract contract = order.getContract();
		boolean allInitialPaymentValued = true;
		for (Order i : Lambda.select(contract.getOrders(), new OrderTypeMatcher(OrderType.INITIAL_PAYMENT))) {
			if (!i.isValued()) {
				allInitialPaymentValued = false;
				break;
			}
		}
		if (allInitialPaymentValued) {
			ProducerTemplate producer = camelContext.createProducerTemplate();
			producer.requestBody("direct:contract_start", contract);
		}
	}
}
