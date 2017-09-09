package org.lab.insurance.domain.core.common;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Language {

	@Id
	private String id;
	private String name;

}
