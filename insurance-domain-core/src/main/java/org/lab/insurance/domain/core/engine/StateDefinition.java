package org.lab.insurance.domain.core.engine;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class StateDefinition {

	@Id
	private String id;

	private String code;
	private String name;
	private Class<?> entityClass;

}
