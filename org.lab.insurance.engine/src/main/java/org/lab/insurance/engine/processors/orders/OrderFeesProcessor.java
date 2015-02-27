package org.lab.insurance.engine.processors.orders;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.jpa.insurance.Order;

public class OrderFeesProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		BigDecimal percent = new BigDecimal("1");
		BigDecimal grossAmount = order.getGrossAmount();
		BigDecimal feesAmount = grossAmount.multiply(percent).divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN);
		BigDecimal netAmount = grossAmount.subtract(feesAmount);
		order.setNetAmount(netAmount);
	}
}
