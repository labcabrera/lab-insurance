package org.lab.insurance.engine.core;

import java.util.Calendar;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.insurance.domain.action.ContractCreation;
import org.lab.insurance.engine.core.config.EngineTestConfig;
import org.lab.insurance.engine.core.config.InsuranceEngineCoreConfig;
import org.lab.insurance.engine.core.domain.InsuranceTask;
import org.lab.insurance.engine.core.domain.repository.InsuranceTaskRepository;
import org.lab.insurance.engine.core.services.InsuranceTaskScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Import({ InsuranceEngineCoreConfig.class, EngineTestConfig.class })
public class InsuranceTaskSchedulerTest {

	@Autowired
	private InsuranceTaskScheduler scheduler;
	@Autowired
	private InsuranceTaskRepository taskRepo;

	@Test
	public void test() {
		taskRepo.deleteAll();

		ContractCreation action = new ContractCreation();
		action.setAgreementCode("AM01");

		InsuranceTask task = InsuranceTask.builder().action(action).execution(Calendar.getInstance().getTime()).build();

		InsuranceTask scheduled = scheduler.schedule(task);

		Assert.assertNotNull(scheduled);
		Assert.assertNotNull(scheduled.getId());

		InsuranceTask readed = taskRepo.findOne(scheduled.getId());

		Assert.assertNotNull(readed);

		ContractCreation actionReaded = readed.getAction(ContractCreation.class);
		Assert.assertEquals(action.getAgreementCode(), actionReaded.getAgreementCode());

	}

}
