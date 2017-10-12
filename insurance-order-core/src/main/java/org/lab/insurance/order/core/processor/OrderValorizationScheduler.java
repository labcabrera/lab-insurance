package org.lab.insurance.order.core.processor;

import java.util.Date;

import org.lab.insurance.domain.action.InsuranceAction;
import org.lab.insurance.domain.action.contract.OrderValorization;
import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.engine.core.domain.InsuranceTask;
import org.lab.insurance.engine.core.services.InsuranceTaskScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class OrderValorizationScheduler {

	@Autowired
	private InsuranceTaskScheduler scheduler;

	public Order process(Order request) {
		Date execution = request.getDates().getValueDate();
		Assert.notNull(execution, "Missing order value date");

		InsuranceAction action = new OrderValorization(request.getId(), execution);

		InsuranceTask task = new InsuranceTask();
		task.setAction(action);
		task.setExecution(execution);

		scheduler.schedule(task);
		return request;
	}

}
