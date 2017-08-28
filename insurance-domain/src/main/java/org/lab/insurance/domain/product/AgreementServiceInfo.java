package org.lab.insurance.domain.product;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Deprecated
@Document
@Data
public class AgreementServiceInfo {

	@Id
	private ObjectId id;
	private String name;

}
