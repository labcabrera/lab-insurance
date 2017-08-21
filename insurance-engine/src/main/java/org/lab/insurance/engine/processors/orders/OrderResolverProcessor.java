package org.lab.insurance.engine.processors.orders;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.lab.insurance.model.HasOrder;
import org.lab.insurance.model.insurance.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderResolverProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(OrderResolverProcessor.class);

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order currentOrder = exchange.getIn().getBody(Order.class);
		HasOrder hasOrder = exchange.getIn().getBody(HasOrder.class);
		String orderId = null;
		if (currentOrder != null && StringUtils.isNoneBlank(currentOrder.getId())) {
			orderId = currentOrder.getId();
		} else if (hasOrder != null && hasOrder.getOrder() != null && StringUtils.isNotBlank(hasOrder.getOrder().getId())) {
			orderId = hasOrder.getOrder().getId();
		}
		if (orderId != null) {
			LOG.debug("Resolving order with id {}", orderId);
			Order order = entityManagerProvider.get().find(Order.class, orderId);
			exchange.getIn().setBody(order);
		} else {
			throw new RuntimeException("Cant resolve order. Message: " + exchange.getIn().getBody());
		}
	}
}
