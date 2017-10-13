package org.lab.insurance.domain.action.contract;

import org.lab.insurance.domain.core.common.calendar.HolidayCalendar;

import lombok.Data;

@Data
public class OrderValorizationAgreementInfo {

	HolidayCalendar calendar;
	Integer valorizationOffset;

}
