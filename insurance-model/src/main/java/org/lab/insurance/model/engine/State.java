package org.lab.insurance.model.engine;

import java.util.Date;

import lombok.Data;

@Data
public class State {

	private StateDefinition stateDefinition;
	
	private Date entered;
	private String hasStateId;

}
