package org.lab.insurance.test.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.commons.lang3.Validate;
import org.joda.time.DateTime;
import org.junit.Test;
import org.lab.insurance.core.math.BigMath;
import org.lab.insurance.engine.ActionExecutionRunner;
import org.lab.insurance.engine.ActionExecutionService;
import org.lab.insurance.engine.guice.InsuranceCoreModule;
import org.lab.insurance.engine.model.orders.PaymentReception;
import org.lab.insurance.engine.model.policy.NewPolicyAction;
import org.lab.insurance.model.Constants;
import org.lab.insurance.model.common.Message;
import org.lab.insurance.model.jpa.accounting.PortfolioMathProvision;
import org.lab.insurance.model.jpa.common.Address;
import org.lab.insurance.model.jpa.common.IdCard;
import org.lab.insurance.model.jpa.common.IdCardType;
import org.lab.insurance.model.jpa.common.Person;
import org.lab.insurance.model.jpa.contract.Agreement;
import org.lab.insurance.model.jpa.contract.Contract;
import org.lab.insurance.model.jpa.contract.ContractRelationType;
import org.lab.insurance.model.jpa.contract.PolicyEntityRelation;
import org.lab.insurance.model.jpa.insurance.BaseAsset;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.model.jpa.insurance.OrderDates;
import org.lab.insurance.model.jpa.insurance.OrderDistribution;
import org.lab.insurance.model.jpa.insurance.OrderType;
import org.lab.insurance.services.insurance.MathProvisionService;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;

public class NewPolicyActionTest {

	// TODO cambio del funcionamiento para meter la recepcion del pago inicial
	@Test
	public void test() {
		try {
			Injector injector = Guice.createInjector(new InsuranceCoreModule());
			injector.getInstance(PersistService.class).start();
			Provider<EntityManager> entityManagerProvider = injector.getProvider(EntityManager.class);
			ActionExecutionService actionExecutionService = injector.getInstance(ActionExecutionService.class);
			EntityManager entityManager = entityManagerProvider.get();

			// Accion de grabacion de la poliza
			Date newPolicyDate = new DateTime(2015, 1, 20, 0, 0, 0, 0).toDate();
			NewPolicyAction action = new NewPolicyAction();
			action.setContract(buildPolicy(entityManager));
			action.setActionDate(newPolicyDate);
			Message<Contract> message = actionExecutionService.execute(action);
			Validate.notNull(message.getPayload());
			Validate.notNull(message.getPayload().getId());
			Validate.isTrue(Message.SUCCESS.equals(message.getCode()));

			// Accion de recepcion del pago inicial
			entityManager.clear();
			Contract readed = entityManager.find(Contract.class, message.getPayload().getId());
			Validate.notNull(readed);
			Validate.isTrue(readed.getCurrentState().getStateDefinition().getId().equals(Constants.PolicyStates.INITIAL));

			Date paymentReceptionDate = new DateTime(2015, 1, 27, 0, 0, 0, 0).toDate();
			Order initialPayment = readed.getOrders().iterator().next();
			PaymentReception paymentReception = new PaymentReception().withActionDate(paymentReceptionDate).withOrderId(initialPayment.getId());
			actionExecutionService.execute(paymentReception);

			Date from = new DateTime(2015, 1, 1, 0, 0, 0, 0).toDate();
			Date to = new DateTime(2015, 12, 31, 0, 0, 0, 0).toDate();
			ActionExecutionRunner actionExecutionRunner = injector.getInstance(ActionExecutionRunner.class);
			actionExecutionRunner.run(from, to);

			Date mpDate = new DateTime(2015, 5, 20, 0, 0, 0, 0).toDate();
			MathProvisionService mpService = injector.getInstance(MathProvisionService.class);
			PortfolioMathProvision mp = mpService.findAtDate(readed.getPortfolioInfo().getPortfolioPassive(), mpDate, true);
			System.out.println("MP: " + mp);
			System.out.println("MP value: " + mp.getValue());

		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	private Contract buildPolicy(EntityManager entityManager) {
		Contract policy = new Contract();
		policy.setAgreement(findAgreement("23000", entityManager));
		policy.setEffective(Calendar.getInstance().getTime());
		policy.setRelations(new ArrayList<PolicyEntityRelation>());
		PolicyEntityRelation relationSuscriptor = new PolicyEntityRelation();
		relationSuscriptor.setStartDate(policy.getEffective());
		relationSuscriptor.setLegalEntity(buildSuscriptor());
		relationSuscriptor.setContract(policy);
		relationSuscriptor.setType(ContractRelationType.SUSCRIPTOR);
		relationSuscriptor.setRelationPercent(BigMath.HUNDRED);
		policy.getRelations().add(relationSuscriptor);
		PolicyEntityRelation relationRecipient = new PolicyEntityRelation();
		relationRecipient.setStartDate(policy.getEffective());
		relationRecipient.setLegalEntity(buildSuscriptor());
		relationRecipient.setContract(policy);
		relationRecipient.setType(ContractRelationType.RECIPIENT);
		relationRecipient.setRelationPercent(BigMath.HUNDRED);
		policy.getRelations().add(relationRecipient);
		policy.setOrders(new ArrayList<Order>());
		policy.getOrders().add(buildInitialPayment(entityManager));
		return policy;
	}

	private Person buildSuscriptor() {
		Person person = new Person();
		person.setName("John");
		person.setFirstSurname("Doe");
		person.setSecondSurname("Smith");
		person.setIdCard(new IdCard());
		person.getIdCard().setNumber("70111222A");
		person.getIdCard().setType(IdCardType.SPAIN_DNI);
		person.setPostalAddress(new Address());
		person.getPostalAddress().setRoadName("Random Street 3, 2ºA");
		return person;
	}

	private Order buildInitialPayment(EntityManager entityManager) {
		Order order = new Order();
		order.setDates(new OrderDates());
		order.getDates().setEffective(Calendar.getInstance().getTime());
		order.setType(OrderType.INITIAL_PAYMENT);
		order.setGrossAmount(new BigDecimal("100000"));
		order.setBuyDistribution(new ArrayList<OrderDistribution>());
		order.getBuyDistribution().add(new OrderDistribution().withPercent(new BigDecimal("75")).withAsset(findAsset("TEST000001", entityManager)));
		order.getBuyDistribution().add(new OrderDistribution().withPercent(new BigDecimal("25")).withAsset(findAsset("EURO1", entityManager)));
		return order;

	}

	private Agreement findAgreement(String code, EntityManager entityManager) {
		return entityManager.createNamedQuery("Agreement.selectByCode", Agreement.class).setParameter("code", code).getSingleResult();
	}

	private BaseAsset findAsset(String isin, EntityManager entityManager) {
		return entityManager.createNamedQuery("BaseAsset.selectByIsin", BaseAsset.class).setParameter("isin", isin).getSingleResult();
	}
}
