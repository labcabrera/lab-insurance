package org.lab.insurance.domain.geo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Province {

	@Id
	private ObjectId id;
	private String name;
	private Country country;

}
