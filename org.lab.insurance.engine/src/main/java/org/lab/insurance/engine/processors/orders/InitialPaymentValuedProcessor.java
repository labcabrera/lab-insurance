package org.lab.insurance.engine.processors.orders;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.Constants;
import org.lab.insurance.model.jpa.contract.Contract;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.model.jpa.insurance.OrderType;
import org.lab.insurance.model.matchers.OrderTypeMatcher;
import org.lab.insurance.services.common.StateMachineService;

import ch.lambdaj.Lambda;

/**
 * Procesador que se ejecuta cuando se valoriza un pago inicial.
 */
public class InitialPaymentValuedProcessor implements Processor {

	@Inject
	private StateMachineService stateMachineService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		Contract policy = order.getContract();
		boolean allInitialPaymentValued = true;
		for (Order i : Lambda.select(policy.getOrders(), new OrderTypeMatcher(OrderType.INITIAL_PAYMENT))) {
			if (!i.isValued()) {
				allInitialPaymentValued = false;
				break;
			}
		}
		if (allInitialPaymentValued) {
			stateMachineService.createTransition(policy, Constants.PolicyStates.ACTIVE);
		}
	}
}
