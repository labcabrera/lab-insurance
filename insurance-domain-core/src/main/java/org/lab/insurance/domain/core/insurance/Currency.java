package org.lab.insurance.domain.core.insurance;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class Currency {

	@Id
	private String id;

	private String iso;
	private String name;

}
