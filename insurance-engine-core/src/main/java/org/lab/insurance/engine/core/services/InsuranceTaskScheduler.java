package org.lab.insurance.engine.core.services;

import java.util.ArrayList;
import java.util.List;

import org.lab.insurance.engine.core.domain.InsuranceTask;
import org.lab.insurance.engine.core.domain.repository.InsuranceTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class InsuranceTaskScheduler {

	@Autowired
	private InsuranceTaskRepository taskRepo;

	public List<InsuranceTask> schedule(List<InsuranceTask> tasks) {
		List<InsuranceTask> result = new ArrayList<>();
		for (InsuranceTask i : tasks) {
			result.add(schedule(i));
		}
		return result;
	}

	public InsuranceTask schedule(InsuranceTask task) {
		Assert.notNull(task, "Missing task");
		Assert.notNull(task.getExecution(), "Missing task execution date");

		taskRepo.save(task);
		return task;
	}

}
