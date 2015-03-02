package org.lab.insurance.engine.processors.orders;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.HasOrder;
import org.lab.insurance.model.jpa.insurance.Order;

public class OrderResolverProcessor implements Processor {

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	@Override
	public void process(Exchange exchange) throws Exception {
		HasOrder hasOrder = exchange.getIn().getBody(HasOrder.class);
		String orderId = hasOrder.getOrder().getId();
		Order order = entityManagerProvider.get().find(Order.class, orderId);
		exchange.getIn().setBody(order);
	}
}
