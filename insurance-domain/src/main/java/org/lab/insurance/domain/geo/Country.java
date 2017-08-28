package org.lab.insurance.domain.geo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Country {

	@Id
	private ObjectId id;

	private String name;
	private String iso2;
	private String iso3;

}
