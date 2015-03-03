package org.lab.insurance.engine.processors.orders;

import javax.persistence.criteria.Order;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class PaymentReceptionProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
	}

}
