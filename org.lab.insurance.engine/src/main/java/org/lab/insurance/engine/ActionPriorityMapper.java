package org.lab.insurance.engine;

import org.lab.insurance.engine.model.ActionEntity;
import org.lab.insurance.engine.model.orders.AccountOrderAction;
import org.lab.insurance.engine.model.orders.ProcessOrderAction;
import org.lab.insurance.engine.model.orders.ValorizateOrderAction;

public class ActionPriorityMapper {

	private static final int DEFAULT_PRIORITY = 100;

	public Integer getPriority(ActionEntity<?> actionEntity) {
		if (ProcessOrderAction.class.isAssignableFrom(actionEntity.getClass())) {
			return 120;
		} else if (ValorizateOrderAction.class.isAssignableFrom(actionEntity.getClass())) {
			return 140;
		} else if (AccountOrderAction.class.isAssignableFrom(actionEntity.getClass())) {
			return 160;
		}
		return DEFAULT_PRIORITY;
	}

}
