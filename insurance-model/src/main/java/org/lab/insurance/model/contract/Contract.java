package org.lab.insurance.model.contract;

import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.lab.insurance.model.HasState;
import org.lab.insurance.model.engine.State;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.product.Agreement;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
@Entity
public class Contract implements HasState<String> {

	@Id
	private String id;

	@NotNull
	private String number;

	@NotNull
	@Reference
	private Agreement agreement;

	private ContractPorfolioInfo portfolioInfo;

	private State currentState;

	private List<PolicyEntityRelation> relations;

	private List<FinancialService> financialServices;

	@Reference
	private List<Order> orders;

	private ContractDates dates;

	@Reference
	private List<ContractLetter> letters;

}
