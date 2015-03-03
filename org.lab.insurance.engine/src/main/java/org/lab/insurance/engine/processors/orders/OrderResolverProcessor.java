package org.lab.insurance.engine.processors.orders;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.HasOrder;
import org.lab.insurance.model.jpa.insurance.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderResolverProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(OrderResolverProcessor.class);

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	@Override
	public void process(Exchange exchange) throws Exception {
		HasOrder hasOrder = exchange.getIn().getBody(HasOrder.class);
		String orderId = hasOrder.getOrder().getId();
		LOG.debug("Resolving order with id {}", orderId);
		Order order = entityManagerProvider.get().find(Order.class, orderId);
		exchange.getIn().setBody(order);
	}
}
