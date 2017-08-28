package org.lab.insurance.engine.processors.orders;

import java.util.Date;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.domain.insurance.Order;
import org.lab.insurance.engine.ActionExecutionService;
import org.lab.insurance.engine.model.orders.ValorizateOrderAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleOrderValorization implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(ScheduleOrderValorization.class);

	@Inject
	private ActionExecutionService actionExecutionService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		LOG.debug("Scheduling valorization for order {}", order);
		ValorizateOrderAction action = new ValorizateOrderAction().withOrderId(order.getId());
		Date when = order.getDates().getValueDate();
		actionExecutionService.schedule(action, when);
	}
}
