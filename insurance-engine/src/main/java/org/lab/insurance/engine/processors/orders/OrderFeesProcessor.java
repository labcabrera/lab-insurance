package org.lab.insurance.engine.processors.orders;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.insurance.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderFeesProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(OrderFeesProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		LOG.debug("Processing fees in order {}", order);
		BigDecimal percent = new BigDecimal("1");
		BigDecimal grossAmount = order.getGrossAmount();
		BigDecimal feesAmount = grossAmount.multiply(percent).divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN);
		BigDecimal netAmount = grossAmount.subtract(feesAmount);
		order.setNetAmount(netAmount);
	}
}
