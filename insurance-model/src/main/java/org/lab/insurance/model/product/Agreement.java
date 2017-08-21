package org.lab.insurance.model.product;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * Representa un acuerdo marco.
 */
@Document
@Data
public class Agreement {

	@Id
	private String id;

	private String name;
	private String code;

	private AgreementServiceInfo serviceInfo;
	private AgreementValidationInfo validationInfo;

	private Date startDate;
	private Date endDate;

}
