package org.lab.insurance.model.legalentity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Profession {

	@Id
	private ObjectId id;
	private String name;
	private String code;

}
