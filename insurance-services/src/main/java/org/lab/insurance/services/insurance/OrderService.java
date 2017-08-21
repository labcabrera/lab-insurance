package org.lab.insurance.services.insurance;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.lab.insurance.model.Constants;
import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.insurance.Order;

public class OrderService {

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	public List<Order> selectByContractInStates(Contract contract, List<String> states) {
		EntityManager entityManager = entityManagerProvider.get();
		String qlString = "select e from Order e where e.contract = :contract and e.currentState.stateDefinition.id in :stateIds order by e.dates.valueDate";
		TypedQuery<Order> query = entityManager.createQuery(qlString, Order.class);
		query.setParameter("stateIds", states);
		query.setParameter("contract", contract);
		return query.getResultList();
	}

	public List<Order> selectByContractNotInStates(Contract contract, String states) {
		EntityManager entityManager = entityManagerProvider.get();
		String qlString = "select e from Order e where e.contract = :contract and e.currentState.stateDefinition.id not in :stateIds order by e.dates.valueDate";
		TypedQuery<Order> query = entityManager.createQuery(qlString, Order.class);
		query.setParameter("stateIds", Arrays.asList(Constants.OrderStates.INITIAL));
		query.setParameter("contract", contract);
		return query.getResultList();
	}
}
