package org.lab.insurance.domain.core.contract;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.NotNull;

import org.lab.insurance.domain.core.HasActivationRange;
import org.lab.insurance.domain.core.HasContract;
import org.lab.insurance.domain.core.legalentity.Person;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(of = "id")
@Document
public class ContractPersonRelation implements HasContract, HasActivationRange {

	@Id
	@ApiModelProperty(value = "Identifier")
	private String id;

	@ApiModelProperty(value = "Contract relation type")
	@NotNull
	private RelationType type;

	@Reference
	@JsonIgnore
	@ApiModelProperty(value = "Contract")
	@NotNull
	private Contract contract;

	@DBRef
	@ApiModelProperty(value = "Entity")
	@NotNull
	private Person person;

	private Date startDate;
	private Date endDate;

	private BigDecimal relationPercent;

}
