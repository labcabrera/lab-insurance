package org.lab.insurance.bdd.contract.creation;

import java.util.Date;

import org.joda.time.DateTime;
import org.lab.insurance.bdd.common.MongoTestOperations;
import org.lab.insurance.common.services.TimestampProvider;
import org.lab.insurance.engine.core.services.InsuranceTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class GeneralSteps extends BddSupport {

	@Autowired
	private TimestampProvider timeStampProvider;

	@Autowired
	protected MongoTestOperations mongoTestOperations;

	@Autowired
	private InsuranceTaskExecutor executor;

	@When("^Inicializo la base de datos$")
	public void inicializo_la_base_de_datos() {
		mongoTestOperations.resetDataBase();
	}

	@When("^establezco la fecha del sistema a (\\d+)/(\\d+)/(\\d+)$")
	public void la_fecha_del_sistema_a(int year, int monthOfYear, int dayOfMonth) {
		Date date = new DateTime(year, monthOfYear, dayOfMonth, 0, 0).toDate();
		timeStampProvider.setFakeDate(date);
	}

	@Then("^Simulo una ejecucion de (\\d+)/(\\d+)/(\\d+) a (\\d+)/(\\d+)/(\\d+)$")
	public void simulo_una_ejecucion_de_a(int fromY, int fromM, int fromD, int toY, int toM, int toD) {
		DateTime from = new DateTime(fromY, fromM, fromD, 0, 0);
		DateTime to = new DateTime(toY, toM, toD, 0, 0);
		DateTime tmp = from;
		while (tmp.compareTo(to) <= 0) {
			timeStampProvider.setFakeDate(tmp.toDate());
			executor.execute(tmp.toDate(), tmp.toDate(), null);
			tmp = tmp.plusDays(1);
		}
	}

}
