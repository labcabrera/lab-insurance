package org.lab.insurance.contract.creation.core.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentReceptionData {

	private String contractId;
	private Date paymentReception;
	

}
