package org.lab.insurance.domain.insurance;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Currency {

	@Id
	private ObjectId id;

	private String iso;
	private String name;

}
