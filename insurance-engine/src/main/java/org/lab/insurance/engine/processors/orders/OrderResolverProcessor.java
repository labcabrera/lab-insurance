package org.lab.insurance.engine.processors.orders;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.lab.insurance.model.HasOrder;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.insurance.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderResolverProcessor implements Processor {

	@Autowired
	private OrderRepository orderRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order currentOrder = exchange.getIn().getBody(Order.class);
		HasOrder hasOrder = exchange.getIn().getBody(HasOrder.class);
		String orderId = null;
		if (currentOrder != null && StringUtils.isNoneBlank(currentOrder.getId())) {
			orderId = currentOrder.getId();
		}
		else if (hasOrder != null && hasOrder.getOrder() != null
				&& StringUtils.isNotBlank(hasOrder.getOrder().getId())) {
			orderId = hasOrder.getOrder().getId();
		}
		if (orderId != null) {
			log.debug("Resolving order with id {}", orderId);
			Order order = orderRepository.findOne(orderId);
			exchange.getIn().setBody(order);
		}
		else {
			throw new RuntimeException("Cant resolve order. Message: " + exchange.getIn().getBody());
		}
	}
}
