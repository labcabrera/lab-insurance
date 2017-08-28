package org.lab.insurance.engine.processors.orders;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.domain.insurance.Order;
import org.lab.insurance.services.common.TimestampProvider;

public class PaymentReceptionDateProcessor implements Processor {

	@Inject
	private TimestampProvider timestampProvider;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		order.getDates().setEffective(timestampProvider.getCurrentDate());
	}
}
