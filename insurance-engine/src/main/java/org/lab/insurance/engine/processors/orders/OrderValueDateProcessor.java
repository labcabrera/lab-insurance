package org.lab.insurance.engine.processors.orders;

import java.util.Date;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.services.common.CalendarService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderValueDateProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(OrderValueDateProcessor.class);

	@Inject
	private CalendarService calendarService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		LOG.debug("Calculating value date for order {}", order);
		Date effective = order.getDates().getEffective();
		Date valueDate = calendarService.getNextLaboralDay(effective, 2);
		order.getDates().setValueDate(valueDate);
	}
}
