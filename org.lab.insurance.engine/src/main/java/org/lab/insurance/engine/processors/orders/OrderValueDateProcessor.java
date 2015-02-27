package org.lab.insurance.engine.processors.orders;

import java.util.Date;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.services.common.CalendarService;

public class OrderValueDateProcessor implements Processor {

	@Inject
	private CalendarService calendarService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		Date effective = order.getDates().getEffective();
		Date valueDate = calendarService.getNextLaboralDay(effective, 2);
		order.getDates().setValueDate(valueDate);
	}
}
