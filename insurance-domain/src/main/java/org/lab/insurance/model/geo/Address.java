package org.lab.insurance.model.geo;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Address {

	@Id
	private ObjectId id;

	private String roadName;
	private String roadNumber;
	private Country country;
	private Province province;
	private Locality locality;
	private String zipCode;

}
