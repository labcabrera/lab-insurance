package org.lab.insurance.model.portfolio;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.lab.insurance.model.HasState;
import org.lab.insurance.model.engine.State;
import org.lab.insurance.model.insurance.BaseAsset;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Investment implements HasState<String> {

	@Id
	private ObjectId id;

	@DBRef
	private BaseAsset asset;

	private State currentState;
	private Date startDate;
	private Date endDate;

	private List<PortfolioOperation> inputOperations;

	private List<PortfolioOperation> outputOperations;

}
