package org.lab.insurance.domain.portfolio;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class PortfolioMathProvision {

	private ObjectId id;
	private Portfolio portfolio;
	private BigDecimal value;
	private Date valueDate;
	private Date generated;
	private List<MathProvision> mathProvisions;

}
