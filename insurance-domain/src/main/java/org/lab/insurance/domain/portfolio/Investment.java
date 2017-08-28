package org.lab.insurance.domain.portfolio;

import java.util.Date;
import java.util.List;

import org.lab.insurance.domain.HasState;
import org.lab.insurance.domain.engine.State;
import org.lab.insurance.domain.insurance.Asset;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Investment implements HasState<String> {

	@Id
	private String id;

	@DBRef
	private Asset asset;

	private State currentState;
	private Date startDate;
	private Date endDate;

	private List<PortfolioOperation> inputOperations;

	private List<PortfolioOperation> outputOperations;

}
