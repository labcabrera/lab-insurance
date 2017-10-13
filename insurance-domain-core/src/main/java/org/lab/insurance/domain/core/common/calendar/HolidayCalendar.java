package org.lab.insurance.domain.core.common.calendar;

import org.lab.insurance.domain.core.geo.Country;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class HolidayCalendar {

	@Id
	private String id;
	private String name;
	private Country country;

}
