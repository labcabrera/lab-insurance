package org.lab.insurance.services.insurance;

import java.util.List;

import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.insurance.Order;

public class OrderService {

	public List<Order> selectByContractInStates(Contract contract, List<String> states) {
		throw new RuntimeException("Not implemented jpa -> mongo");
		// EntityManager entityManager = entityManagerProvider.get();
		// String qlString = "select e from Order e where e.contract = :contract and e.currentState.stateDefinition.id
		// in :stateIds order by e.dates.valueDate";
		// TypedQuery<Order> query = entityManager.createQuery(qlString, Order.class);
		// query.setParameter("stateIds", states);
		// query.setParameter("contract", contract);
		// return query.getResultList();
	}

	public List<Order> selectByContractNotInStates(Contract contract, String states) {
		throw new RuntimeException("Not implemented jpa -> mongo");
		// EntityManager entityManager = entityManagerProvider.get();
		// String qlString = "select e from Order e where e.contract = :contract and e.currentState.stateDefinition.id
		// not in :stateIds order by e.dates.valueDate";
		// TypedQuery<Order> query = entityManager.createQuery(qlString, Order.class);
		// query.setParameter("stateIds", Arrays.asList(Constants.OrderStates.INITIAL));
		// query.setParameter("contract", contract);
		// return query.getResultList();
	}
}
