package org.lab.insurance.domain.engine;

import java.util.Date;

import org.lab.insurance.domain.HasCode;

import lombok.Data;

@Data
public class State {

	private HasCode code;
	private Date entered;
	private String hasStateId;

}
