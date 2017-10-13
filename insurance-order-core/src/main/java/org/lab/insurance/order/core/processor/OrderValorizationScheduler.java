package org.lab.insurance.order.core.processor;

import java.util.Date;

import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.engine.core.domain.InsuranceTask;
import org.lab.insurance.engine.core.services.InsuranceTaskScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class OrderValorizationScheduler {

	@Autowired
	private Environment env;

	@Autowired
	private InsuranceTaskScheduler scheduler;

	public Order process(Order request) {
		Date execution = request.getDates().getValueDate();
		Assert.notNull(execution, "Missing order value date");

		InsuranceTask task = new InsuranceTask();
		task.setData(new Order(request.getId()));
		task.setDestinationQueue(env.getProperty("queues.order.valorization"));
		task.setExecution(execution);

		scheduler.schedule(task);
		return request;
	}

}
