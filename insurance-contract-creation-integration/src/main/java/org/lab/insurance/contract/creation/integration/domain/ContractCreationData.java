package org.lab.insurance.contract.creation.integration.domain;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.lab.insurance.model.contract.ContractPersonRelation;
import org.lab.insurance.model.insurance.Order;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Informacion de contratacion")
public class ContractCreationData {

	@NotNull
	@ApiModelProperty(value = "Acuerdo marco")
	private String agreementCode;

	@NotEmpty
	@NotNull
	@ApiModelProperty(value = "Relaciones del contrato")
	private List<ContractPersonRelation> relations;

	@NotNull
	@ApiModelProperty("Pago incial")
	private Order initialPayment;

}
