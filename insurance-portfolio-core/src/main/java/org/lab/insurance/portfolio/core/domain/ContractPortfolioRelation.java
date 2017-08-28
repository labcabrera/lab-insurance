package org.lab.insurance.portfolio.core.domain;

import java.util.List;

import org.lab.insurance.domain.contract.Contract;
import org.lab.insurance.domain.portfolio.Portfolio;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class ContractPortfolioRelation {

	@Id
	private String id;

	@DBRef
	private Contract contract;

	@DBRef
	private List<Portfolio> portfolios;

}
