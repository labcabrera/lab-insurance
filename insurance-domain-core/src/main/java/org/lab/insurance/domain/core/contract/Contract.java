package org.lab.insurance.domain.core.contract;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.lab.insurance.domain.core.HasState;
import org.lab.insurance.domain.core.common.audit.AuditData;
import org.lab.insurance.domain.core.engine.State;
import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.domain.core.insurance.OrderType;
import org.lab.insurance.domain.core.product.Agreement;
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
public class Contract implements HasState {

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

	// TODO problema al registrar el serializador
	@JsonIgnore
	@Null(message = "STATE_MUST_BE_EMPTY", groups = ValidationContext.Insert.class)
	private State currentState;

	@Null(message = "CONTRACT_DATES_MUST_BE_EMPTY", groups = ValidationContext.Insert.class)
	private ContractDates dates;

	@DBRef
	private List<ContractPersonRelation> relations;

	@Transient // TODO
	private List<FinancialService> financialServices;

	private AuditData auditData;

	@Reference
	@JsonIgnore
	@DBRef
	private List<Order> orders;

	@Reference
	@JsonIgnore
	@Transient // TODO
	private List<ContractLetter> letters;

	public List<Order> filterOrders(OrderType type) {
		return orders.stream().filter(x -> type.equals(x.getType())).collect(Collectors.toList());
	}

	public enum States {
		INITIAL, VALIDATED, APPROVED, PAID, STARTED, CANCELLED;
	}

	public static class ValidationContext {

		public static interface Insert {
		}

		public static interface Default {
		}

	}

}
