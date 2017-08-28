package org.lab.insurance.services.common;

import javax.inject.Inject;

import org.lab.insurance.domain.HasState;

public class StateMachineService {

	@Inject
	private TimestampProvider timestampProvider;

	public void createTransition(HasState<String> hasState, String stateDefinitionId) {
		throw new RuntimeException("Not implemented jpa -> mongo");
		// StateDefinition stateDefinition = entityManager.find(StateDefinition.class, stateDefinitionId);
		// State state = new State();
		// state.setEntered(timestampProvider.getCurrentDate());
		// state.setStateDefinition(stateDefinition);
		// state.setHasStateId(hasState.getId());
		// hasState.setCurrentState(state);
		// entityManager.merge(hasState);
		// entityManager.persist(state);
		// entityManager.flush();
	}
}
