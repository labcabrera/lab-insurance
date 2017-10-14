package org.lab.insurance.domain.core.common.audit;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class InsuranceAuditEvent {

	@Id
	String id;

	@NotNull
	Date timeStamp;

	@NotNull
	String name;

	String category;

	Object data;

}
