package com.cnp.ciis.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.commons.lang.Validate;
import org.junit.Test;
import org.lab.insurance.engine.ActionExecutionService;
import org.lab.insurance.engine.guice.InsuranceCoreModule;
import org.lab.insurance.model.common.Message;
import org.lab.insurance.model.engine.actions.policy.NewPolicyAction;
import org.lab.insurance.model.jpa.Agreement;
import org.lab.insurance.model.jpa.Person;
import org.lab.insurance.model.jpa.Policy;
import org.lab.insurance.model.jpa.PolicyEntityRelation;
import org.lab.insurance.model.jpa.PolicyRelationType;
import org.lab.insurance.model.jpa.insurance.BaseAsset;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.model.jpa.insurance.OrderDates;
import org.lab.insurance.model.jpa.insurance.OrderDistribution;
import org.lab.insurance.model.jpa.insurance.OrderType;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;

public class NewPolicyActionTest {

	@Test
	public void test() {
		try {
			Injector injector = Guice.createInjector(new InsuranceCoreModule());
			injector.getInstance(PersistService.class).start();
			Provider<EntityManager> entityManagerProvider = injector.getProvider(EntityManager.class);
			ActionExecutionService service = injector.getInstance(ActionExecutionService.class);
			EntityManager entityManager = entityManagerProvider.get();

			NewPolicyAction action = new NewPolicyAction();
			action.setPolicy(buildPolicy(entityManager));
			Message<Policy> message = service.execute(action);
			Validate.notNull(message.getPayload());
			Validate.notNull(message.getPayload().getId());
			Validate.isTrue(Message.SUCCESS.equals(message.getCode()));

			Policy readed = entityManager.find(Policy.class, message.getPayload().getId());
			Validate.notNull(readed);
			for (PolicyEntityRelation i : readed.getRelations()) {
				System.out.println(i);
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			throw ex;
		}
	}

	private Policy buildPolicy(EntityManager entityManager) {
		Policy policy = new Policy();
		policy.setAgreement(findAgreement("23000", entityManager));
		policy.setEffective(Calendar.getInstance().getTime());
		policy.setRelations(new ArrayList<PolicyEntityRelation>());
		PolicyEntityRelation relationSuscriptor = new PolicyEntityRelation();
		relationSuscriptor.setStartDate(policy.getEffective());
		relationSuscriptor.setLegalEntity(buildSuscriptor());
		relationSuscriptor.setPolicy(policy);
		relationSuscriptor.setType(PolicyRelationType.SUSCRIPTOR);
		policy.getRelations().add(relationSuscriptor);
		PolicyEntityRelation relationRecipient = new PolicyEntityRelation();
		relationRecipient.setStartDate(policy.getEffective());
		relationRecipient.setLegalEntity(buildSuscriptor());
		relationRecipient.setPolicy(policy);
		relationRecipient.setType(PolicyRelationType.RECIPIENT);
		relationRecipient.setRelationPercent(new BigDecimal("100"));
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
