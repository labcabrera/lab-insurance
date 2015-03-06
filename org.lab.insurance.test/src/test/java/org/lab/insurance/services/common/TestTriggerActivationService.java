package org.lab.insurance.services.common;

import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.lab.insurance.engine.guice.InsuranceCoreModule;
import org.lab.insurance.model.jpa.system.TriggerDefinition;
import org.lab.insurance.model.jpa.system.TriggerType;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;

public class TestTriggerActivationService {

	private static Injector INJECTOR;

	@BeforeClass
	public static void initializeTest() {
		INJECTOR = Guice.createInjector(new InsuranceCoreModule());
		INJECTOR.getInstance(PersistService.class).start();
	}

	@AfterClass
	public static void finalizeTest() {
		INJECTOR.getInstance(PersistService.class).stop();
	}

	@Test
	public void test_1() {
		check(new DateTime(2015, 3, 5, 0, 0, 0, 0).toDate(), new DateTime(2015, 3, 25, 0, 0, 0, 0).toDate(), buildFixedLaboralDayMontlyForwardTrigger(25));
	}

	@Test
	public void test_2() {
		check(new DateTime(2015, 3, 28, 0, 0, 0, 0).toDate(), new DateTime(2015, 4, 25, 0, 0, 0, 0).toDate(), buildFixedLaboralDayMontlyForwardTrigger(25));
	}

	private void check(Date when, Date expected, TriggerDefinition definition) {
		TriggerActivationService service = INJECTOR.getInstance(TriggerActivationService.class);
		Date activation = service.getNextActivation(definition, when);
		System.out.println("When      : " + DateFormatUtils.ISO_DATE_FORMAT.format(when));
		System.out.println("Activation: " + DateFormatUtils.ISO_DATE_FORMAT.format(activation));
		System.out.println("Expected  : " + DateFormatUtils.ISO_DATE_FORMAT.format(expected));
		if (!activation.equals(expected)) {
			Assert.fail();
		}
	}

	private TriggerDefinition buildFixedLaboralDayMontlyForwardTrigger(int day) {
		TriggerDefinition triggerDefinition = new TriggerDefinition();
		triggerDefinition.setType(TriggerType.FIXED_LABORAL_DAY_MONTLY_FORWARD);
		triggerDefinition.setValues(new HashMap<String, String>());
		triggerDefinition.getValues().put(TriggerDefinition.KEY_DAY, String.valueOf(day));
		return triggerDefinition;
	}

}
