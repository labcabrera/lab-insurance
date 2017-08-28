package org.lab.insurance.domain.legalentity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Profession {

	@Id
	private String id;
	private String name;
	private String code;

}
