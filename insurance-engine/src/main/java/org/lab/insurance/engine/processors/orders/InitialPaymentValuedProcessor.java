package org.lab.insurance.engine.processors.orders;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.domain.Constants;
import org.lab.insurance.domain.contract.Contract;
import org.lab.insurance.domain.insurance.Order;
import org.lab.insurance.domain.insurance.OrderType;
import org.lab.insurance.engine.ActionExecutionService;
import org.lab.insurance.engine.model.contract.ContractStartAction;

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

		List<Order> initialPayments = contract.getOrders().stream()
				.filter(x -> OrderType.INITIAL_PAYMENT.equals(x.getType())).collect(Collectors.toList());

		for (Order i : initialPayments) {
			if (!isValued(i)) {
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

	public boolean isValued(Order i) {
		String currentStateId = (i.getCurrentState() != null) ? i.getCurrentState().getStateDefinition().getCode()
				: null;
		boolean checkDate = i.getDates() != null && i.getDates().getValued() != null;
		boolean checkState = currentStateId != null && (currentStateId.equals(Constants.OrderStates.VALUED)
				|| currentStateId.equals(Constants.OrderStates.ACCOUNTED));
		return checkDate && checkState;
	}
}
