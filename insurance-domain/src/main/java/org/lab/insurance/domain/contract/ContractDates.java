package org.lab.insurance.domain.contract;

import java.util.Date;

import lombok.Data;

@Data
public class ContractDates {

	private Date effective;
	private Date startDate;
	private Date endDate;
}
