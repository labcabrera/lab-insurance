package org.lab.insurance.bdd.contract.creation.steps;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.lab.insurance.bdd.contract.creation.BddSupport;
import org.lab.insurance.common.exception.InsuranceException;
import org.lab.insurance.domain.action.contract.ContractApprobation;
import org.lab.insurance.domain.action.contract.ContractCreation;
import org.lab.insurance.domain.action.contract.InitialPaymentReception;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.contract.ContractPersonRelation;
import org.lab.insurance.domain.core.contract.RelationType;
import org.lab.insurance.domain.core.contract.repository.ContractRepository;
import org.lab.insurance.domain.core.insurance.Asset;
import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.domain.core.insurance.OrderDistribution;
import org.lab.insurance.domain.core.insurance.OrderType;
import org.lab.insurance.domain.core.insurance.repository.OrderRepository;
import org.lab.insurance.domain.core.legalentity.Person;
import org.lab.insurance.domain.core.legalentity.repository.PersonRepository;
import org.lab.insurance.engine.core.domain.InsuranceTask;
import org.lab.insurance.engine.core.services.InsuranceTaskScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.lab.insurance.contract.creation.gateway.integration.ContractCreationGateway;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContractCreationSteps extends BddSupport {

	@Autowired
	protected ContractCreationGateway contractCreationGateway;

	@Autowired
	protected ContractRepository contractRepository;

	@Autowired
	protected PersonRepository personRepository;

	@Autowired
	protected OrderRepository orderRepository;

	@Autowired
	protected InsuranceTaskScheduler scheduler;

	protected ContractCreation contractCreateAction;
	protected Contract contract;
	protected Order initialPayment;
	protected String contractNumber;

	@When("^preparo contrato con acuerdo (\\w+)$")
	public void preparo_contrato(String agreementCode) {
		contractCreateAction = new ContractCreation();
		contractCreateAction.setAgreementCode(agreementCode);
	}

	@When("^establezco como suscriptor del contrato a la persona identificada con (\\w+)$")
	public void establezco_como_suscriptor_del_contrato_a_la_persona_identificada_con_(String idCardNumber) {
		Person person = personRepository.findByIdCardNumber(idCardNumber);
		Assert.assertNotNull(person);
		addRelation(person, RelationType.SUSCRIPTOR);
	}

	@When("^establezco como beneficiario del contrato a la persona identificada con (\\w+)$")
	public void establezco_como_beneficiario_del_contrato_a_la_persona_identificada_con_W(String idCardNumber) {
		Assert.assertNotNull(contractCreateAction);
		Person person = personRepository.findByIdCardNumber(idCardNumber);
		Assert.assertNotNull(person);
		addRelation(person, RelationType.RECIPIENT);
	}

	@When("^establezco un pago inicial bruto de ([\\d|\\.]+) euros$")
	public void establezco_un_pago_inicial_neto_de_euros(BigDecimal amount) {
		Order initialPayment = Order.builder().type(OrderType.INITIAL_PAYMENT).build();
		initialPayment.setGrossAmount(amount);
		contractCreateAction.setInitialPayment(initialPayment);
	}

	@When("^establezco la distribucion del pago inicial en \\((.+)\\)$")
	public void establezco_la_distribucion_del_pago_inicial_en_ASSET_ASSET(String distribution) {
		Order initialPayment = contractCreateAction.getInitialPayment();
		initialPayment.setBuyDistribution(new ArrayList<>());
		log.debug("Parsing distribution {}", distribution);
		StringTokenizer stringTokenizer = new StringTokenizer(distribution, ";");
		while (stringTokenizer.hasMoreTokens()) {
			String token = stringTokenizer.nextToken();
			String[] split = token.split(":");
			String isin = StringUtils.trimAllWhitespace(split[0]);
			OrderDistribution i = new OrderDistribution();
			i.setAsset(Asset.builder().isin(isin).build());
			i.setPercent(new BigDecimal(StringUtils.trimWhitespace(split[1].replaceAll("%", ""))));
			initialPayment.getBuyDistribution().add(i);
		}
	}

	@When("^establezco la fecha de contratacion a (\\d+)/(\\d+)/(\\d+)$")
	public void establezco_la_fecha_de_contratacion_a(int yyyy, int mm, int dd) throws Throwable {
		DateTime date = new DateTime(yyyy, mm, dd, 0, 0, 0);
		contractCreateAction.setEffective(date.toDate());
	}

	@When("^muestro el JSON del contrato$")
	public void muestro_el_JSON_del_contrato() throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		log.debug(mapper.writeValueAsString(contract));
	}

	@When("^programo la aprobacion del contrato a fecha (\\d+)/(\\d+)/(\\d+) con el id del contrato$")
	public void programo_la_aprobacion_del_contrato_a_fecha_con_el_id_del_contrato(int year, int monthOfYear,
			int dayOfMonth) {
		Date execution = new DateTime(year, monthOfYear, dayOfMonth, 0, 0).toDate();
		ContractApprobation action = ContractApprobation.builder().contractId(contract.getId()).build();
		InsuranceTask task = InsuranceTask.builder().action(action).execution(execution).build();
		scheduler.schedule(task);
	}

	@When("^programo la accion de recepcion de pago a fecha (\\d+)/(\\d+)/(\\d+) con el id del contrato$")
	public void programo_la_accion_de_recepcion_de_pago_a_fecha_con_el_id_del_contrato(int year, int monthOfYear,
			int dayOfMonth) {
		Date execution = new DateTime(year, monthOfYear, dayOfMonth, 0, 0).toDate();
		InitialPaymentReception action = InitialPaymentReception.builder().paymentReception(execution)
				.contractId(contract.getId()).build();
		InsuranceTask task = InsuranceTask.builder().action(action).execution(execution).build();
		scheduler.schedule(task);
	}

	@When("^invoco al servicio de contratacion$")
	public void invoco_al_servicio_de_contratacion() {
		contract = contractCreationGateway.processCreation(contractCreateAction);
		Assert.assertNotNull(contract.getId());
	}

	@Then("^espero hasta que el estado de contrato sea \"([^\"]*)\" \\(timeout: (\\d+)sg\\)$")
	public void espero_hasta_que_el_estado_de_contrato_sea_timeout_sg(String status, int timeoutInSg) {
		log.info("Waiting until contract status is {}", status);
		long timeout = System.currentTimeMillis() + 1000 * timeoutInSg;
		Contract checkContract = null;
		while (System.currentTimeMillis() < timeout) {
			checkContract = contractRepository.findById(contract.getId()).get();
			if (status.equals(checkContract.getCurrentState().getCode())) {
				log.info("Contract status is {}", status);
				return;
			}
			try {
				Thread.sleep(1000);
			}
			catch (Exception ignore) {
			}
		}
		throw new InsuranceException("Invalid contract status: " + checkContract.getCurrentState().getCode());
	}

	@Then("^espero hasta que el estado del pago inicial sea \"([^\"]*)\" \\(timeout: (\\d+)sg\\)$")
	public void espero_segundos_hasta_que_el_estado_del_pago_inicial_sea(String status, int timeoutInSg) {
		log.info("Waiting until initial payment status is {}", status);
		long timeout = System.currentTimeMillis() + 1000 * timeoutInSg;
		Contract checkContract = null;
		Order checkOrder = null;
		while (System.currentTimeMillis() < timeout) {
			checkContract = contractRepository.findById(contract.getId()).get();
			checkOrder = checkContract.filterOrders(OrderType.INITIAL_PAYMENT).iterator().next();
			if (status.equals(checkOrder.getCurrentState().getCode())) {
				log.info("Initial payment status is {}", status);
				return;
			}
			try {
				Thread.sleep(1000);
			}
			catch (Exception ignore) {
			}
		}
		throw new InsuranceException("Invalid inital payment status: " + checkOrder.getCurrentState().getCode());
	}

	@Then("^recupero el numero del contrato$")
	public void recupero_el_numero_del_contrato() {
		contractNumber = contract.getNumber();
		Assert.assertNotNull(contractNumber);
		log.debug("Contract number: {}", contract.getNumber());
	}

	@Then("^recupero el contrato de base de datos a partir del numero$")
	public void recupero_el_contrato_de_base_de_datos_a_partir_del_numero() {
		contract = contractRepository.findByNumber(contractNumber);
		Assert.assertNotNull(contract);
	}

	@Then("^verifico que el suscriptor es (\\w+)$")
	public void verifico_que_el_suscriptor_es_Z(String idCardNumber) {
		List<ContractPersonRelation> relations = contract.getRelations();
		Assert.assertNotNull(relations);

		List<ContractPersonRelation> filtered = relations.stream()
				.filter(x -> RelationType.SUSCRIPTOR.equals(x.getType())).collect(Collectors.toList());

		Assert.assertTrue(filtered.size() == 1);
		Assert.assertTrue(filtered.iterator().next().getPerson().getIdCard().getNumber().equals(idCardNumber));
	}

	@Then("^verifico que el estado del contrato es \"([^\"]*)\"$")
	public void verifico_que_el_estado_del_contrato_es(String state) {
		contract = contractRepository.findById(contract.getId()).get();
		Assert.assertEquals(state, contract.getCurrentState().getCode());
	}

	@Then("^verifico que el estado del pago inicial es \"([^\"]*)\"$")
	public void verifico_que_el_estado_del_pago_inicial_es(String state) {
		contract = contractRepository.findById(contract.getId()).get();
		Order order = contract.filterOrders(OrderType.INITIAL_PAYMENT).iterator().next();
		Assert.assertEquals(state, order.getCurrentState().getCode());
	}

	private void addRelation(Person person, RelationType type) {
		ContractPersonRelation relation = new ContractPersonRelation();
		relation.setContract(contract);
		relation.setPerson(person);
		relation.setType(type);
		if (contractCreateAction.getRelations() == null) {
			contractCreateAction.setRelations(new ArrayList<>());
		}
		contractCreateAction.getRelations().add(relation);
	}

}
