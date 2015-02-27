package org.lab.insurance.services.common;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.lab.insurance.model.HasState;
import org.lab.insurance.model.jpa.engine.State;
import org.lab.insurance.model.jpa.engine.StateDefinition;

public class StateMachineService {

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	@Inject
	private TimestampProvider timestampProvider;

	public void createTransition(HasState<String> hasState, String stateDefinitionId) {
		EntityManager entityManager = entityManagerProvider.get();
		StateDefinition stateDefinition = entityManager.find(StateDefinition.class, stateDefinitionId);
		if (stateDefinition == null) {
			stateDefinition = new StateDefinition();
			stateDefinition.setId(stateDefinitionId);
			stateDefinition.setName(stateDefinitionId);
			entityManager.persist(stateDefinition);
			entityManager.flush();
		}
		State state = new State();
		state.setEntered(timestampProvider.getCurrentDate());
		state.setStateDefinition(stateDefinition);
		state.setHasStateId(hasState.getId());
		hasState.setCurrentState(state);
		entityManager.persist(state);
		entityManager.flush();
	}
}
