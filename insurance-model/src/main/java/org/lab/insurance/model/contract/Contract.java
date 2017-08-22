package org.lab.insurance.model.contract;

import java.util.List;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.lab.insurance.model.HasState;
import org.lab.insurance.model.engine.State;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.product.Agreement;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@Document
@Entity
public class Contract implements HasState<String> {

	@Id
	@Null(message = "ID_MUST_BE_EMPTY", groups = ValidationContext.Insert.class)
	@NotNull(message = "ID_MANDATORY", groups = ValidationContext.Default.class)
	@ApiModelProperty(value = "Identifier")
	private String id;

	@Null(message = "CONTRACT_NUMBER_MUST_BE_EMPTY", groups = ValidationContext.Insert.class)
	@NotNull(message = "CONTRACT_NUMBER_MANDATORY", groups = ValidationContext.Default.class)
	@ApiModelProperty(value = "Contract number")
	private String number;

	@NotNull(message = "AGREEMENT_MANDATORY",
			groups = { ValidationContext.Insert.class, ValidationContext.Default.class })
	@Reference
	@ApiModelProperty(value = "Agreement")
	private Agreement agreement;

	@Null(message = "STATE_MUST_BE_EMPTY", groups = ValidationContext.Insert.class)
	private State currentState;

	@Null(message = "CONTRACT_DATES_MUST_BE_EMPTY", groups = ValidationContext.Insert.class)
	private ContractDates dates;

	@Null(message = "CONTRACT_PORTFOLIO_INFO_MUST_BE_EMPTY", groups = ValidationContext.Insert.class)
	private ContractPorfolioInfo portfolioInfo;

	@DBRef
	private List<PolicyEntityRelation> relations;

	@Transient // TODO
	private List<FinancialService> financialServices;

	@Reference
	@JsonIgnore
	@Transient // TODO
	private List<Order> orders;

	@Reference
	@JsonIgnore
	@Transient // TODO
	private List<ContractLetter> letters;

	public static class ValidationContext {

		public static interface Insert {
		}

		public static interface Default {
		}

	}

}
