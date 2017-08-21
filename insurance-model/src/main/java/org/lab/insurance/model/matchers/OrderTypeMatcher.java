package org.lab.insurance.model.matchers;

import org.apache.commons.lang3.Validate;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.insurance.OrderType;

public class OrderTypeMatcher extends BaseMatcher<Order> {

	private final OrderType type;

	public OrderTypeMatcher(OrderType type) {
		Validate.notNull(type);
		this.type = type;
	}

	@Override
	public boolean matches(Object item) {
		boolean result = false;
		if (item != null) {
			if (Order.class.isAssignableFrom(item.getClass())) {
				Order entity = (Order) item;
				if (entity.getType() == type) {
					result = true;
				}
			}
		}
		return result;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("no coindide con el tipo de  " + type);
	}
}
