package org.lab.insurance.domain.legalentity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class IdCard {

	private String number;
	private IdCardType type;

}
