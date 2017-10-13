package org.lab.insurance.engine.core.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;

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
@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceTask {

	@Id
	String id;

	@NotNull
	String destinationQueue;

	@NotNull
	Object data;

	@NotNull
	Date execution;

	Date executed;

	InsuranceTaskError error;

	Integer priority;

	@ApiModelProperty(value = "Tags", notes = "String classifier to filter by task groups")
	List<String> tags;

	@DBRef
	InsuranceTask parent;

}
