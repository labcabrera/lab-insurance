package org.lab.insurance.bdd.contract.creation;

import java.util.Date;

import org.joda.time.DateTime;
import org.lab.insurance.common.services.TimestampProvider;
import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GeneralSteps extends BddSupport {
	
//	@Autowired
//	private Insurancet

	@Autowired
	private TimestampProvider timeStampProvider;

	@When("^la fecha del sistema a (\\d+)/(\\d+)/(\\d+)$")
	public void la_fecha_del_sistema_a(int year, int monthOfYear, int dayOfMonth) {
		Date date = new DateTime(year, monthOfYear, dayOfMonth, 0, 0).toDate();
		timeStampProvider.setFakeDate(date);
	}

	@Then("^Simulo una ejecucion de (\\d+)/(\\d+)/(\\d+) a (\\d+)/(\\d+)/(\\d+)$")
	public void simulo_una_ejecucion_de_a(int arg1, int arg2, int arg3, int arg4, int arg5, int arg6) {
	}

}
