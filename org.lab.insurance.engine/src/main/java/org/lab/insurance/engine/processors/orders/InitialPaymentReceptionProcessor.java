package org.lab.insurance.engine.processors.orders;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.Constants;
import org.lab.insurance.model.jpa.contract.Contract;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.services.common.StateMachineService;
import org.lab.insurance.services.common.TimestampProvider;
import org.lab.insurance.services.insurance.OrderService;

/**
 * Procesador que se ejecuta cuando se recibe la confirmacion de un pago inicial.
 *
 */
public class InitialPaymentReceptionProcessor implements Processor {

	@Inject
	private StateMachineService stateMachineService;
	@Inject
	private TimestampProvider timestampProvider;
	@Inject
	private Provider<EntityManager> entityManagerProvider;
	@Inject
	private OrderService orderService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		Contract contract = order.getContract();
		boolean checkStarted = checkContractStarted(contract);
		if (checkStarted) {
			contract.setStartDate(timestampProvider.getCurrentDate());
			stateMachineService.createTransition(contract, Constants.ContractStates.PAYED);
			EntityManager entityManager = entityManagerProvider.get();
			entityManager.merge(contract);
		}
	}

	public boolean checkContractStarted(Contract policy) {
		List<Order> orders = orderService.selectByContractNotInStates(policy, Constants.OrderStates.INITIAL);
		return orders.size() != 0;
	}

}
