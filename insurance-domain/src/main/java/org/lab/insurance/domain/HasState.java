package org.lab.insurance.domain;

import org.lab.insurance.domain.engine.State;

public interface HasState extends HasIdentifier<String> {

	State getCurrentState();

	void setCurrentState(State state);

}
