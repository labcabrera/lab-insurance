package org.lab.insurance.services.common;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.lab.insurance.model.HasState;
import org.lab.insurance.model.engine.State;
import org.lab.insurance.model.engine.StateDefinition;

public class StateMachineService {

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	@Inject
	private TimestampProvider timestampProvider;

	public void createTransition(HasState<String> hasState, String stateDefinitionId) {
		EntityManager entityManager = entityManagerProvider.get();
		StateDefinition stateDefinition = entityManager.find(StateDefinition.class, stateDefinitionId);
		State state = new State();
		state.setEntered(timestampProvider.getCurrentDate());
		state.setStateDefinition(stateDefinition);
		state.setHasStateId(hasState.getId());
		hasState.setCurrentState(state);
		entityManager.merge(hasState);
		entityManager.persist(state);
		entityManager.flush();

	}
}
