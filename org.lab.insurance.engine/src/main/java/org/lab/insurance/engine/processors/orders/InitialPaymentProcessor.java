package org.lab.insurance.engine.processors.orders;

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

public class InitialPaymentProcessor implements Processor {

	@Inject
	private StateMachineService stateMachineService;
	@Inject
	private TimestampProvider timestampProvider;
	@Inject
	private Provider<EntityManager> entityManagerProvider;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		Policy policy = order.getPolicy();
		// TODO comprobar que todos los pagos estan ok
		policy.setStartDate(timestampProvider.getCurrentDate());
		stateMachineService.createTransition(policy, Constants.PolicyStates.PAYED);
		EntityManager entityManager = entityManagerProvider.get();
		entityManager.merge(policy);
	}
}
