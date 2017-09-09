package org.lab.insurance.domain.action;

import java.util.Date;

import lombok.Data;

@Data
public abstract class AbstractInsuranceAction implements InsuranceAction {

	Date execution;

}
