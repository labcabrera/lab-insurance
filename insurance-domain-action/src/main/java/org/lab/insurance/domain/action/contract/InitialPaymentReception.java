package org.lab.insurance.domain.action.contract;

import java.util.Date;

import org.lab.insurance.domain.action.AbstractInsuranceAction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InitialPaymentReception extends AbstractInsuranceAction {

	String contractId;
	Date paymentReception;

}
