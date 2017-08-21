package org.lab.insurance.model.legalentity;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdCard {

	@Id
	private String id;

	private String number;
	private IdCardType type;

}
