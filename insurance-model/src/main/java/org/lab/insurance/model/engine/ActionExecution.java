package org.lab.insurance.model.engine;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class ActionExecution {

	@Id
	private ObjectId id;

	private Date executed;
	private Date scheduled;
	private Date cancelled;
	private Date failure;
	private Class<?> actionEntityClass;
	private String actionEntityJson;
	private String resultJson;
	private Integer priority;

}
