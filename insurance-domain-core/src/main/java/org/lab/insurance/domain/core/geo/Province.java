package org.lab.insurance.domain.core.geo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Document
@Data
public class Province {

	@Id
	private String id;
	private String name;

	@JsonIgnore
	private Country country;

}
