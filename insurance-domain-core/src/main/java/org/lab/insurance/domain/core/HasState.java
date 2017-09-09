package org.lab.insurance.domain.core;

import org.lab.insurance.domain.core.engine.State;

public interface HasState {

	State getCurrentState();

	void setCurrentState(State state);

}
