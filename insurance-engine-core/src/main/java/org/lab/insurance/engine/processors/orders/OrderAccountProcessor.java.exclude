package org.lab.insurance.engine.processors.orders;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.domain.insurance.Order;
import org.lab.insurance.services.accounting.AccountingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderAccountProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(OrderAccountProcessor.class);

	@Inject
	private AccountingService accountingService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		LOG.debug("Accounting order {}", order);
		accountingService.account(order);
	}

}
