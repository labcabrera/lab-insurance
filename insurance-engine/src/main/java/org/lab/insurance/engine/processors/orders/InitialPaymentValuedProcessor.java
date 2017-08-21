package org.lab.insurance.engine.processors.orders;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.engine.ActionExecutionService;
import org.lab.insurance.engine.model.contract.ContractStartAction;
import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.insurance.OrderType;
import org.lab.insurance.model.matchers.OrderTypeMatcher;

import ch.lambdaj.Lambda;

/**
 * Procesador que se ejecuta cuando se valoriza un pago inicial.
 */
public class InitialPaymentValuedProcessor implements Processor {

	@Inject
	private ActionExecutionService actionExecutionService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		Contract contract = order.getContract();
		boolean allInitialPaymentValued = true;
		for (Order i : Lambda.select(contract.getOrders(), new OrderTypeMatcher(OrderType.INITIAL_PAYMENT))) {
			if (!i.isValued()) {
				allInitialPaymentValued = false;
				break;
			}
		}
		if (allInitialPaymentValued) {
			ContractStartAction action = new ContractStartAction();
			action.setContract(new Contract());
			action.getContract().setId(contract.getId());
			action.setActionDate(order.getDates().getValued());
			actionExecutionService.execute(action);
		}
	}
}
