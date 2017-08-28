package org.lab.insurance.domain.engine;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class StateDefinition {

	@Id
	private ObjectId id;
	
	private String code;
	private String name;
	private Class<?> entityClass;

}
