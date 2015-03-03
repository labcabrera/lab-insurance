package org.lab.insurance.engine.processors.orders;

import java.util.Date;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang.Validate;
import org.lab.insurance.model.Constants;
import org.lab.insurance.model.engine.ActionEntity;
import org.lab.insurance.model.engine.actions.orders.ValorizateOrderAction;
import org.lab.insurance.model.exceptions.CancelAndRexecuteException;
import org.lab.insurance.model.exceptions.NoCotizationException;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.services.common.CalendarService;
import org.lab.insurance.services.common.StateMachineService;
import org.lab.insurance.services.common.TimestampProvider;
import org.lab.insurance.services.insurance.ValorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderValorizationProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(OrderValorizationProcessor.class);

	@Inject
	private ValorizationService valorizationService;
	@Inject
	private TimestampProvider timestampProvider;
	@Inject
	private CalendarService calendarService;
	@Inject
	private StateMachineService stateMachineService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		LOG.debug("Processing order valorization for order {}", order);
		validateOrder(order);
		try {
			valorizationService.valorizate(order);
			stateMachineService.createTransition(order, Constants.OrderStates.VALUED);
		} catch (NoCotizationException ex) {
			// Si no existen precios preprogramamos la valorizacion para el dia siguiente
			Date now = timestampProvider.getCurrentDate();
			Date reexecutionDate = calendarService.getNextLaboralDay(now, 1);
			ActionEntity<?> actionEntity = new ValorizateOrderAction().withOrderId(order.getId()).withActionDate(reexecutionDate);
			throw new CancelAndRexecuteException(actionEntity, reexecutionDate, ex.getMessage(), ex);
		}
	}

	private void validateOrder(Order order) {
		Validate.notNull(order.getDates().getEffective(), "order.validation.missingEffective");
		Validate.notNull(order.getDates().getValueDate(), "order.validation.missingValueDate");
		Validate.isTrue(order.getDates().getValued() == null, "order.validation.valuedDateMustBeNull");
		Validate.isTrue(order.getMarketOrders().size() > 0, "order.validation.missingMarketOrders");
	}
}
