package org.lab.insurance.ms.contract.creation.domain;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.lab.insurance.model.contract.ContractPersonRelation;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.product.Agreement;

import lombok.Data;

@Data
public class ContractCreationData {

	@NotNull
	private Agreement agreement;

	@NotEmpty
	@NotNull
	private List<ContractPersonRelation> relations;

	@NotNull
	private Order initialPayment;

}
