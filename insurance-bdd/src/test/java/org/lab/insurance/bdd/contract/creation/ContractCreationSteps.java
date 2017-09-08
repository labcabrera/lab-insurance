package org.lab.insurance.bdd.contract.creation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.lab.insurance.bdd.contract.MongoTestOperations;
import org.lab.insurance.contract.creation.core.domain.ContractCreationData;
import org.lab.insurance.contract.creation.core.domain.PaymentReceptionData;
import org.lab.insurance.domain.contract.Contract;
import org.lab.insurance.domain.contract.ContractPersonRelation;
import org.lab.insurance.domain.contract.RelationType;
import org.lab.insurance.domain.contract.repository.ContractRepository;
import org.lab.insurance.domain.insurance.Asset;
import org.lab.insurance.domain.insurance.Order;
import org.lab.insurance.domain.insurance.OrderDistribution;
import org.lab.insurance.domain.insurance.OrderType;
import org.lab.insurance.domain.legalentity.Person;
import org.lab.insurance.domain.legalentity.repository.PersonRepository;
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
	protected MongoTestOperations mongoTestOperations;

	protected ContractCreationData contractCreateInfo;
	protected Contract contract;
	protected String contractNumber;

	@When("^Inicializo la base de datos$")
	public void inicializo_la_base_de_datos() {
		mongoTestOperations.resetDataBase();
	}

	@When("^Preparo contrato con acuerdo (\\w+)$")
	public void preparo_contrato(String agreementCode) {
		contractCreateInfo = new ContractCreationData();
		contractCreateInfo.setAgreementCode(agreementCode);
	}

	@When("^Establezco como suscriptor del contrato a la persona identificada con (\\w+)$")
	public void establezco_como_suscriptor_del_contrato_a_la_persona_identificada_con_(String idCardNumber) {
		Person person = personRepository.findByIdCardNumber(idCardNumber);
		Assert.assertNotNull(person);
		addRelation(person, RelationType.SUSCRIPTOR);
	}

	@When("^Establezco como beneficiario del contrato a la persona identificada con (\\w+)$")
	public void establezco_como_beneficiario_del_contrato_a_la_persona_identificada_con_W(String idCardNumber) {
		Assert.assertNotNull(contractCreateInfo);
		Person person = personRepository.findByIdCardNumber(idCardNumber);
		Assert.assertNotNull(person);
		addRelation(person, RelationType.RECIPIENT);
	}

	private void addRelation(Person person, RelationType type) {
		ContractPersonRelation relation = new ContractPersonRelation();
		relation.setContract(contract);
		relation.setPerson(person);
		relation.setType(type);
		if (contractCreateInfo.getRelations() == null) {
			contractCreateInfo.setRelations(new ArrayList<>());
		}
		contractCreateInfo.getRelations().add(relation);

	}

	@When("^Establezco un pago inicial bruto de ([\\d|\\.]+) euros$")
	public void establezco_un_pago_inicial_neto_de_euros(BigDecimal amount) {
		Order initialPayment = Order.builder().type(OrderType.INITIAL_PAYMENT).build();
		initialPayment.setGrossAmount(amount);
		contractCreateInfo.setInitialPayment(initialPayment);
	}

	@When("^Establezco la distribucion del pago inicial en \\((.+)\\)$")
	public void establezco_la_distribucion_del_pago_inicial_en_ASSET_ASSET(String distribution) {
		Order initialPayment = contractCreateInfo.getInitialPayment();
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

	@When("^Establezco la fecha de contratacion a (\\d+)/(\\d+)/(\\d+)$")
	public void establezco_la_fecha_de_contratacion_a(int arg1, int arg2, int arg3) {
		// TODO
	}

	@When("^Muestro el JSON del contrato$")
	public void muestro_el_JSON_del_contrato() throws Throwable {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		log.debug(mapper.writeValueAsString(contract));
	}

	@Then("^Invoco al servicio de contratacion$")
	public void invoco_al_servicio_de_contratacion() {
		contract = contractCreationGateway.processCreation(contractCreateInfo);
		Assert.assertNotNull(contract.getId());
	}

	@Then("^Apruebo el contrato$")
	public void apruebo_el_contrato() {
		contract = contractCreationGateway.processApprobation(contract);
	}

	@Then("^Establezco la recepcion del pago inicial a (\\d+)/(\\d+)/(\\d+)$")
	public void establezco_la_recepcion_del_pago_inicial_a(int year, int monthOfYear, int dayOfMonth) {
		Date date = new DateTime(year, monthOfYear, dayOfMonth, 0, 0).toDate();
		PaymentReceptionData request = PaymentReceptionData.builder().paymentReception(date)
				.contractId(contract.getId()).build();
		contract = contractCreationGateway.processPaymentReception(request);
	}

	@Then("^Recupero el numero del contrato$")
	public void recupero_el_numero_del_contrato() {
		contractNumber = contract.getNumber();
		Assert.assertNotNull(contractNumber);
		log.debug("Contract number: {}", contract.getNumber());
	}

	@Then("^Recupero el contrato de base de datos a partir del numero$")
	public void recupero_el_contrato_de_base_de_datos_a_partir_del_numero() {
		contract = contractRepository.findByNumber(contractNumber);
		Assert.assertNotNull(contract);
	}

	@Then("^Verifico que el suscriptor es (\\w+)$")
	public void verifico_que_el_suscriptor_es_Z(String idCardNumber) {
		List<ContractPersonRelation> relations = contract.getRelations();
		Assert.assertNotNull(relations);

		List<ContractPersonRelation> filtered = relations.stream()
				.filter(x -> RelationType.SUSCRIPTOR.equals(x.getType())).collect(Collectors.toList());

		Assert.assertTrue(filtered.size() == 1);
		Assert.assertTrue(filtered.iterator().next().getPerson().getIdCard().getNumber().equals(idCardNumber));
	}

}
