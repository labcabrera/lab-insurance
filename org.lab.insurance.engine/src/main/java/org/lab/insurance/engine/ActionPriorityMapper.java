package org.lab.insurance.engine;

import org.lab.insurance.model.engine.ActionEntity;
import org.lab.insurance.model.engine.actions.orders.AccountOrderAction;
import org.lab.insurance.model.engine.actions.orders.ProcessOrderAction;
import org.lab.insurance.model.engine.actions.orders.ValorizateOrderAction;

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
