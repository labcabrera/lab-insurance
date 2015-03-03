package org.lab.insurance.engine.camel.predicates;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.model.jpa.insurance.OrderType;

public class OrderTypePredicate implements Predicate {

	public static OrderTypePredicate withType(OrderType type) {
		return new OrderTypePredicate(type);
	}

	private final OrderType type;

	public OrderTypePredicate(OrderType type) {
		this.type = type;
	}

	@Override
	public boolean matches(Exchange exchange) {
		Order order = exchange.getIn().getBody(Order.class);
		boolean result = false;
		if (order != null && order.getType() == type) {
			result = true;
		}
		return result;
	}

}
