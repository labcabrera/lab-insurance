package org.lab.insurance.test.actions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.joda.time.DateTime;
import org.junit.Test;
import org.lab.insurance.core.math.BigMath;
import org.lab.insurance.engine.ActionExecutionService;
import org.lab.insurance.engine.DailyActionExecutionRunner;
import org.lab.insurance.engine.guice.InsuranceCoreModule;
import org.lab.insurance.engine.model.contract.NewContractAction;
import org.lab.insurance.engine.model.orders.PaymentReception;
import org.lab.insurance.engine.model.orders.SwitchAction;
import org.lab.insurance.model.common.Message;
import org.lab.insurance.model.jpa.contract.Contract;
import org.lab.insurance.model.jpa.contract.ContractRelationType;
import org.lab.insurance.model.jpa.contract.PolicyEntityRelation;
import org.lab.insurance.model.jpa.geo.Address;
import org.lab.insurance.model.jpa.insurance.BaseAsset;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.model.jpa.insurance.OrderDates;
import org.lab.insurance.model.jpa.insurance.OrderDistribution;
import org.lab.insurance.model.jpa.insurance.OrderType;
import org.lab.insurance.model.jpa.legalentity.IdCard;
import org.lab.insurance.model.jpa.legalentity.IdCardType;
import org.lab.insurance.model.jpa.legalentity.Person;
import org.lab.insurance.model.jpa.portfolio.PortfolioMathProvision;
import org.lab.insurance.model.jpa.product.Agreement;
import org.lab.insurance.services.common.TimestampProvider;
import org.lab.insurance.services.insurance.MathProvisionService;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;

/**
 * Test que simula la contratacion de la poliza el ciclo de vida basico durante un año.
 *
 */
public class NewContractActionTest {

	@Test
	public void test() {
		try {
			Injector injector = Guice.createInjector(new InsuranceCoreModule());
			injector.getInstance(PersistService.class).start();
			Provider<EntityManager> entityManagerProvider = injector.getProvider(EntityManager.class);
			ActionExecutionService actionExecutionService = injector.getInstance(ActionExecutionService.class);
			TimestampProvider timestampProvider = injector.getInstance(TimestampProvider.class);
			EntityManager entityManager = entityManagerProvider.get();

			// Accion de grabacion de la poliza
			Date newContractDate = new DateTime(2015, 1, 20, 0, 0, 0, 0).toDate();
			NewContractAction newContractAction = new NewContractAction();
			newContractAction.setContract(buildPolicy(entityManager));
			newContractAction.setActionDate(newContractDate);
			timestampProvider.setDate(newContractDate);
			Message<Contract> newContractResult = actionExecutionService.execute(newContractAction);
			String contractId = newContractResult.getPayload().getId();

			// Programamos la accion de recepcion del pago inicial
			Contract readed = entityManager.find(Contract.class, contractId);
			Date paymentReceptionDate = new DateTime(2015, 1, 27, 0, 0, 0, 0).toDate();
			Order initialPayment = readed.getOrders().iterator().next();
			PaymentReception paymentReceptionAction = new PaymentReception().withActionDate(paymentReceptionDate).withOrderId(initialPayment.getId());
			actionExecutionService.schedule(paymentReceptionAction, paymentReceptionDate);

			// Programamos un switch por importe
			SwitchAction switchAction = new SwitchAction();
			Date switchEffective = new DateTime(2015, 5, 15, 0, 0, 0, 0).toDate();
			switchAction.setOrder(buildSwitchOrderByAmount(readed, switchEffective, entityManager));
			actionExecutionService.schedule(switchAction, switchEffective);

			Date from = new DateTime(2015, 1, 1, 0, 0, 0, 0).toDate();
			Date to = new DateTime(2015, 12, 31, 0, 0, 0, 0).toDate();
			DailyActionExecutionRunner actionRunner = injector.getInstance(DailyActionExecutionRunner.class);
			// ActionExecutionRunner actionExecutionRunner = injector.getInstance(ActionExecutionRunner.class);
			actionRunner.run(from, to);

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
		BaseAsset assetTEST000001 = findAsset("TEST000001", entityManager);
		BaseAsset assetEURO1 = findAsset("EURO1", entityManager);
		Order order = new Order();
		order.setDates(new OrderDates());
		order.getDates().setEffective(Calendar.getInstance().getTime());
		order.setType(OrderType.INITIAL_PAYMENT);
		order.setGrossAmount(new BigDecimal("1000000"));
		order.setBuyDistribution(new ArrayList<OrderDistribution>());
		order.getBuyDistribution().add(new OrderDistribution().withPercent(new BigDecimal("75")).withAsset(assetTEST000001));
		order.getBuyDistribution().add(new OrderDistribution().withPercent(new BigDecimal("25")).withAsset(assetEURO1));
		return order;
	}

	private Order buildSwitchOrderByAmount(Contract contract, Date effective, EntityManager entityManager) {
		BaseAsset assetTEST000001 = findAsset("TEST000001", entityManager);
		BaseAsset assetTEST000002 = findAsset("TEST000002", entityManager);
		BaseAsset assetEURO1 = findAsset("EURO1", entityManager);
		Order order = new Order();
		order.setContract(new Contract());
		order.getContract().setId(contract.getId());
		order.setGrossAmount(new BigDecimal("10000"));
		order.setSellDistribution(new ArrayList<OrderDistribution>());
		order.getSellDistribution().add(new OrderDistribution(assetTEST000001).withPercent(new BigDecimal("50")));
		order.getSellDistribution().add(new OrderDistribution(assetEURO1).withPercent(new BigDecimal("50")));
		order.setBuyDistribution(new ArrayList<OrderDistribution>());
		order.getBuyDistribution().add(new OrderDistribution(assetTEST000002).withPercent(new BigDecimal("100")));
		order.setDates(new OrderDates());
		order.getDates().setEffective(effective);
		return order;
	}

	private Agreement findAgreement(String code, EntityManager entityManager) {
		return entityManager.createNamedQuery("Agreement.selectByCode", Agreement.class).setParameter("code", code).getSingleResult();
	}

	private BaseAsset findAsset(String isin, EntityManager entityManager) {
		return entityManager.createNamedQuery("BaseAsset.selectByIsin", BaseAsset.class).setParameter("isin", isin).getSingleResult();
	}
}
