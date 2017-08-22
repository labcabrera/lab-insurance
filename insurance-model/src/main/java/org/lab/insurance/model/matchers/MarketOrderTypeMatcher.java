package org.lab.insurance.model.matchers;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.lab.insurance.model.insurance.MarketOrder;
import org.lab.insurance.model.insurance.MarketOrderType;
import org.lab.insurance.model.insurance.Order;

public class MarketOrderTypeMatcher extends BaseMatcher<Order> {

	private final MarketOrderType type;

	public MarketOrderTypeMatcher(MarketOrderType type) {
		this.type = type;
	}

	@Override
	public boolean matches(Object item) {
		boolean result = false;
		if (item != null) {
			if (MarketOrder.class.isAssignableFrom(item.getClass())) {
				MarketOrder entity = (MarketOrder) item;
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
