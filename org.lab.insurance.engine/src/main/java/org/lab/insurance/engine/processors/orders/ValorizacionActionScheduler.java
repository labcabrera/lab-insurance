package org.lab.insurance.engine.processors.orders;

import java.util.Date;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.engine.ActionExecutionService;
import org.lab.insurance.model.engine.actions.orders.ValorizateOrderAction;
import org.lab.insurance.model.jpa.insurance.Order;

public class ValorizacionActionScheduler implements Processor {

	@Inject
	private ActionExecutionService actionExecutionService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		ValorizateOrderAction action = new ValorizateOrderAction();
		action.setOrder(new Order());
		action.getOrder().setId(order.getId());
		Date when = order.getDates().getValueDate();
		actionExecutionService.schedule(action, when);
	}
}
