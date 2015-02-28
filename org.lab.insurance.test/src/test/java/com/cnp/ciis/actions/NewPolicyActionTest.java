package com.cnp.ciis.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

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
	}

	private Policy buildPolicy(EntityManager entityManager) {
		Policy policy = new Policy();
		policy.setAgreement(findOrCreateAgreement(entityManager));
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
		order.getBuyDistribution().add(new OrderDistribution().withPercent(new BigDecimal("75")).withAsset(findOrCreateAsset("LU00012345", entityManager)));
		order.getBuyDistribution().add(new OrderDistribution().withPercent(new BigDecimal("25")).withAsset(findOrCreateAsset("CNPEURO1", entityManager)));
		return order;

	}

	private Agreement findOrCreateAgreement(EntityManager entityManager) {
		String code = "23000";
		Agreement agreement = null;
		TypedQuery<Agreement> query = entityManager.createNamedQuery("Agreement.selectByCode", Agreement.class);
		try {
			agreement = query.setParameter("code", code).getSingleResult();
		} catch (NoResultException ex) {
			entityManager.getTransaction().begin();
			agreement = new Agreement();
			agreement.setName(code);
			agreement.setCode(code);
			entityManager.persist(agreement);
			entityManager.getTransaction().commit();
		}
		return agreement;
	}

	private BaseAsset findOrCreateAsset(String isin, EntityManager entityManager) {
		BaseAsset baseAsset = null;
		TypedQuery<BaseAsset> query = entityManager.createNamedQuery("BaseAsset.selectByIsin", BaseAsset.class);
		try {
			baseAsset = query.setParameter("isin", isin).getSingleResult();
		} catch (NoResultException ex) {
			entityManager.getTransaction().begin();
			baseAsset = new BaseAsset();
			baseAsset.setIsin(isin);
			baseAsset.setName(isin);
			entityManager.persist(baseAsset);
			entityManager.getTransaction().commit();
		}
		return baseAsset;
	}
}
