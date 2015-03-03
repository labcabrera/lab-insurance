package org.lab.insurance.engine.processors.orders;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.Constants;
import org.lab.insurance.model.jpa.Policy;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.services.common.StateMachineService;

/**
 * Procesador que se ejecuta cuando se valoriza un pago inicial.
 */
public class InitialPaymentValuedProcessor implements Processor {

	@Inject
	private StateMachineService stateMachineService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		Policy policy = order.getPolicy();
		stateMachineService.createTransition(policy, Constants.PolicyStates.ACTIVE);
	}
}
