package org.lab.insurance.domain.action.contract;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.lab.insurance.domain.action.AbstractInsuranceAction;
import org.lab.insurance.domain.core.contract.ContractPersonRelation;
import org.lab.insurance.domain.core.insurance.Order;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ContractCreation extends AbstractInsuranceAction {

	@NotNull
	@ApiModelProperty(value = "Contract agreement")
	String agreementCode;

	@NotEmpty
	@NotNull
	@ApiModelProperty(value = "Contract relations")
	List<ContractPersonRelation> relations;

	@NotNull
	@ApiModelProperty("Initial payment")
	Order initialPayment;

}
