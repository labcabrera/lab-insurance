package org.lab.insurance.domain.action.contract;

import java.util.Date;

import org.lab.insurance.domain.action.AbstractInsuranceAction;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class InitialPaymentReception extends AbstractInsuranceAction {

	String contractId;
	Date paymentReception;

}
