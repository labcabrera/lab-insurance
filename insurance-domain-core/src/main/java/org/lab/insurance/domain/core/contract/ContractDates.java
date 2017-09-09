package org.lab.insurance.domain.core.contract;

import java.util.Date;

import lombok.Data;

@Data
public class ContractDates {

	private Date effective;
	private Date startDate;
	private Date endDate;
}
