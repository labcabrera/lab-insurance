package org.lab.insurance.engine.core.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.lab.insurance.domain.action.InsuranceAction;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents an action planned to be executed on a certain date.
 */
// TODO considerar hacerla generica
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceTask {

	@Id
	String id;

	@NotNull
	InsuranceAction action;

	@NotNull
	Date execution;

	Date executed;

	InsuranceTaskError error;

	Integer priority;

	@ApiModelProperty(value = "Tags", notes = "String classifier to filter by task groups")
	List<String> tags;

	@DBRef
	InsuranceTask parent;

	@SuppressWarnings("unchecked")
	public <T> T getAction(Class<T> actionClass) {
		return (T) action;
	}

}
