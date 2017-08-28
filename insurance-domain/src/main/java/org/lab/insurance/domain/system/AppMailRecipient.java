package org.lab.insurance.domain.system;

import java.util.Date;

import org.lab.insurance.domain.HasIdentifier;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class AppMailRecipient implements HasIdentifier<String> {

	@Id
	private String id;
	private String name;
	private String mailAddress;
	private Date startDate;
	private Date endDate;

}