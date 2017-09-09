package org.lab.insurance.domain.action;

import java.util.Date;

import lombok.Data;

@Data
public class InitialPaymentReception implements InsuranceAction {

	Date execution;

}
