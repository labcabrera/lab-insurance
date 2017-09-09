package org.lab.insurance.domain.core.contract;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class LetterType {

	@Id
	private String id;
	private String name;

}
