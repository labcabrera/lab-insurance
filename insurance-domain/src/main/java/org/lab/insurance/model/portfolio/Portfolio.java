package org.lab.insurance.model.portfolio;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Portfolio {

	@Id
	private ObjectId id;
	
	private String name;
	private PortfolioType type;
	
	private List<Investment> investments;

}
