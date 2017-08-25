package org.lab.insurance.model;

import org.lab.insurance.model.engine.State;

public interface HasState<T> {

	State getCurrentState();

	void setCurrentState(State state);

}
