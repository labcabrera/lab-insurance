package org.lab.insurance.domain.engine;

import java.util.Date;

import lombok.Data;

@Data
public class State {

	private String code;
	private Date entered;
	private String hasStateId;

}
