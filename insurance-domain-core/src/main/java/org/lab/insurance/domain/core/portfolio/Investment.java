package org.lab.insurance.domain.core.portfolio;

import java.util.Date;
import java.util.List;

import org.lab.insurance.domain.core.HasState;
import org.lab.insurance.domain.core.engine.State;
import org.lab.insurance.domain.core.insurance.Asset;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Document
@Data
public class Investment implements HasState {

	@Id
	private String id;

	@DBRef
	private Asset asset;

	private State currentState;
	private Date startDate;
	private Date endDate;

	@DBRef
	@JsonIgnore
	private List<PortfolioOperation> inputOperations;

	@DBRef
	@JsonIgnore
	private List<PortfolioOperation> outputOperations;

}
