package org.lab.insurance.engine.processors.orders;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.model.jpa.insurance.OrderProcessInfo;

public class OrderPrepareProcessor implements Processor {

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		if (order.getProcessInfo() == null) {
			order.setProcessInfo(new OrderProcessInfo());
		}
		if (order.getId() == null) {
			EntityManager entityManager = entityManagerProvider.get();
			entityManager.persist(order);
			entityManager.flush();
		}
	}
}
