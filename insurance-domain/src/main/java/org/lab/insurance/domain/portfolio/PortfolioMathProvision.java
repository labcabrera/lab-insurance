package org.lab.insurance.domain.portfolio;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class PortfolioMathProvision {

	@Id
	private String id;

	private Portfolio portfolio;
	private BigDecimal value;
	private Date valueDate;
	private Date generated;
	private List<MathProvision> mathProvisions;

}
