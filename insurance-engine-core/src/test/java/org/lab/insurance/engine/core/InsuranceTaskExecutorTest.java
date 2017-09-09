package org.lab.insurance.engine.core;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.insurance.domain.action.ContractCreation;
import org.lab.insurance.engine.core.config.InsuranceEngineCoreConfig;
import org.lab.insurance.engine.core.domain.InsuranceTask;
import org.lab.insurance.engine.core.domain.repository.InsuranceTaskRepository;
import org.lab.insurance.engine.core.services.InsuranceTaskExecutor;
import org.lab.insurance.engine.core.services.InsuranceTaskScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import(InsuranceEngineCoreConfig.class)
public class InsuranceTaskExecutorTest {

	@Autowired
	private InsuranceTaskScheduler scheduler;
	@Autowired
	private InsuranceTaskExecutor executor;
	@Autowired
	private InsuranceTaskRepository taskRepo;

	@Test
	public void test() {
		taskRepo.deleteAll();

		Date date01 = new DateTime(2000, 01, 10, 0, 0).toDate();
		Date date02 = new DateTime(2000, 01, 15, 0, 0).toDate();

		List<String> tags = Arrays.asList("tag01");
		List<String> tagsExcluded = Arrays.asList("tag02");

		ContractCreation action = new ContractCreation();
		action.setAgreementCode("AM01");

		InsuranceTask task01 = InsuranceTask.builder().action(action).execution(date01).tags(tags).build();
		InsuranceTask task02 = InsuranceTask.builder().action(action).execution(date02).tags(tags).build();
		InsuranceTask task03 = InsuranceTask.builder().action(action).execution(date01).tags(tagsExcluded).build();

		List<InsuranceTask> scheduled = scheduler.schedule(Arrays.asList(task01, task02, task03));

		Assert.assertNotNull(scheduled);
		Assert.assertEquals(3, scheduled.size());

		Date from = new DateTime(2000, 01, 9, 0, 0).toDate();
		Date to = new DateTime(2000, 01, 11, 0, 0).toDate();

		executor.execute(from, to, tags);
	}

}
