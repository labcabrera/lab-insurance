package org.lab.insurance.bdd.contract.creation;

import java.util.Date;

import org.joda.time.DateTime;
import org.lab.insurance.common.services.TimestampProvider;
import org.lab.insurance.engine.core.services.InsuranceTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GeneralSteps extends BddSupport {

	@Autowired
	private TimestampProvider timeStampProvider;

	@Autowired
	private InsuranceTaskExecutor executor;

	@When("^la fecha del sistema a (\\d+)/(\\d+)/(\\d+)$")
	public void la_fecha_del_sistema_a(int year, int monthOfYear, int dayOfMonth) {
		Date date = new DateTime(year, monthOfYear, dayOfMonth, 0, 0).toDate();
		timeStampProvider.setFakeDate(date);
	}

	@Then("^Simulo una ejecucion de (\\d+)/(\\d+)/(\\d+) a (\\d+)/(\\d+)/(\\d+)$")
	public void simulo_una_ejecucion_de_a(int fromY, int fromM, int fromD, int toY, int toM, int toD) {
		Date from = new DateTime(fromY, fromM, fromD, 0, 0).toDate();
		Date to = new DateTime(toY, toM, toD, 0, 0).toDate();
		executor.execute(from, to, null);
	}

}
