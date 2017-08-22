package org.lab.insurance.model.insurance;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDates {

	private Date effective;
	private Date valueDate;
	private Date valued;
	private Date processed;
	private Date accounted;

}
