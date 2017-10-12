package org.lab.insurance.bdd.contract.creation.steps;

import java.util.Date;

import org.joda.time.DateTime;
import org.lab.insurance.bdd.common.MongoTestOperations;
import org.lab.insurance.bdd.common.RabbitTestOperations;
import org.lab.insurance.bdd.contract.creation.BddSupport;
import org.lab.insurance.common.exception.InsuranceException;
import org.lab.insurance.common.services.TimestampProvider;
import org.lab.insurance.engine.core.services.InsuranceTaskExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeneralSteps extends BddSupport {

	@Autowired
	private Environment env;

	@Autowired
	private TimestampProvider timeStampProvider;

	@Autowired
	protected MongoTestOperations mongoTestOperations;

	@Autowired
	protected RabbitTestOperations rabbitOperations;

	@Autowired
	private InsuranceTaskExecutor executor;

	@When("^inicializo la base de datos$")
	public void inicializo_la_base_de_datos() {
		mongoTestOperations.resetDataBase();
	}

	@When("^establezco la fecha del sistema a (\\d+)/(\\d+)/(\\d+)$")
	public void la_fecha_del_sistema_a(int year, int monthOfYear, int dayOfMonth) {
		Date date = new DateTime(year, monthOfYear, dayOfMonth, 0, 0).toDate();
		timeStampProvider.setFakeDate(date);
	}

	// TODO no funciona como espero. Si hay mensajes en las colas cuando se inicializa el contexto los mensajes se leen
	// antes de que se ejecute esta sentencia, de modo que podrian afectar a la ejecucion. Esto no sucede por ejemplo en
	// la compilacion de travis dado que la imagen de docker sobre la que se ejecuta no tiene informacion previa.
	@When("^purgo las colas de contratacion$")
	public void purgo_las_colas_de_contratacion() {
		rabbitOperations.purgue(env.getProperty("queues.contract.creation"));
		rabbitOperations.purgue(env.getProperty("queues.contract.approbation"));
		rabbitOperations.purgue(env.getProperty("queues.contract.doc-creation"));
		rabbitOperations.purgue(env.getProperty("queues.order.creation"));
		rabbitOperations.purgue(env.getProperty("queues.portfolio.creation"));
		rabbitOperations.purgue(env.getProperty("queues.payment.initial-payment-reception"));
		log.info("Clean queues");
	}

	@Then("^espero que se vacie la cola \"([^\"]*)\"$")
	public void espero_que_se_vacie_la_cola(String queueName) throws Throwable {
		rabbitOperations.waitUntilQueueIsEmpty(queueName);
	}

	@Then("^espero (\\d+) segundos$")
	public void espero_segundos(int sg) {
		try {
			Thread.sleep(sg * 1000);
		}
		catch (Exception ex) {
			throw new InsuranceException(ex);
		}
	}

	@Then("^simulo una ejecucion de (\\d+)/(\\d+)/(\\d+) a (\\d+)/(\\d+)/(\\d+)$")
	public void simulo_una_ejecucion_de_a(int fromY, int fromM, int fromD, int toY, int toM, int toD) {
		DateTime from = new DateTime(fromY, fromM, fromD, 0, 0);
		DateTime to = new DateTime(toY, toM, toD, 0, 0);
		DateTime tmp = from;
		while (tmp.compareTo(to) <= 0) {
			timeStampProvider.setFakeDate(tmp.toDate());
			executor.execute(tmp.toDate(), tmp.toDate(), null);
			tmp = tmp.plusDays(1);
		}
		log.info("Finalized range action execution");
	}
}
