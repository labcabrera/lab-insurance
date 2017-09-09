package org.lab.insurance.domain.core.engine;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class ActionExecution {

	@Id
	private String id;

	private Date executed;
	private Date scheduled;
	private Date cancelled;
	private Date failure;

	private Class<?> actionEntityClass;

	private String actionEntityJson;
	private String resultJson;
	private Integer priority;

}
