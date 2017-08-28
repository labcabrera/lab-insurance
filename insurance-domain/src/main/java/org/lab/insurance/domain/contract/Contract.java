package org.lab.insurance.domain.contract;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.bson.types.ObjectId;
import org.lab.insurance.domain.HasState;
import org.lab.insurance.domain.common.audit.AuditData;
import org.lab.insurance.domain.engine.State;
import org.lab.insurance.domain.insurance.Order;
import org.lab.insurance.domain.insurance.OrderType;
import org.lab.insurance.domain.product.Agreement;
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
@SuppressWarnings("serial")
public class Contract implements Serializable, HasState<String> {

	@Id
	@Null(message = "ID_MUST_BE_EMPTY", groups = ValidationContext.Insert.class)
	@NotNull(message = "ID_MANDATORY", groups = ValidationContext.Default.class)
	@ApiModelProperty(value = "Identifier")
	private ObjectId id;

	@Null(message = "CONTRACT_NUMBER_MUST_BE_EMPTY", groups = ValidationContext.Insert.class)
	@NotNull(message = "CONTRACT_NUMBER_MANDATORY", groups = ValidationContext.Default.class)
	@ApiModelProperty(value = "Contract number")
	private String number;

	@NotNull(message = "AGREEMENT_MANDATORY", groups = { ValidationContext.Insert.class,
			ValidationContext.Default.class })
	@Reference
	@ApiModelProperty(value = "Agreement")
	private Agreement agreement;

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

	public static class ValidationContext {

		public static interface Insert {
		}

		public static interface Default {
		}

	}

}
