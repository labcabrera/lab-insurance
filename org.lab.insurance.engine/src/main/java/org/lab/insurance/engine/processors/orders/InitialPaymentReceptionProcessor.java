package org.lab.insurance.engine.processors.orders;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.Constants;
import org.lab.insurance.model.jpa.Policy;
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
		Policy policy = order.getPolicy();
		boolean checkStarted = checkPolicyStarted(policy);
		if (checkStarted) {
			policy.setStartDate(timestampProvider.getCurrentDate());
			stateMachineService.createTransition(policy, Constants.PolicyStates.PAYED);
			EntityManager entityManager = entityManagerProvider.get();
			entityManager.merge(policy);
		}
	}

	public boolean checkPolicyStarted(Policy policy) {
		List<Order> orders = orderService.selectByPolicyNotInStates(policy, Constants.OrderStates.INITIAL);
		return orders.size() != 0;
	}

}
