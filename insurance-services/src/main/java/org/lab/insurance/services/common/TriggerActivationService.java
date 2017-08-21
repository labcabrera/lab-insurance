package org.lab.insurance.services.common;

import java.util.Date;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.lab.insurance.model.exceptions.ConfigurationException;
import org.lab.insurance.model.system.TriggerDefinition;

public class TriggerActivationService {

	@Inject
	private CalendarService calendarService;

	public Date getNextActivation(TriggerDefinition triggerDefinition, Date when) {
		switch (triggerDefinition.getType()) {
		case FIXED_LABORAL_DAY_MONTLY_FORWARD:
			return evalFixedLaboralDayMontly(triggerDefinition, when);
		default:
			throw new RuntimeException("Not implemented");
		}
	}

	private Date evalFixedLaboralDayMontly(TriggerDefinition triggerDefinition, Date when) {
		String strDay = triggerDefinition.getValues().get(TriggerDefinition.KEY_DAY);
		if (strDay == null || !strDay.matches("\\d+")) {
			throw new ConfigurationException(
					"Trigger " + triggerDefinition + " has not valid param " + TriggerDefinition.KEY_DAY);
		}
		Integer targetDay = Integer.parseInt(strDay);
		DateTime currentDate = new DateTime(when);
		DateTime targetDate = new DateTime(currentDate.getYear(), currentDate.getMonthOfYear(), targetDay, 0, 0, 0, 0);
		if (targetDate.isBefore(currentDate)) {
			targetDate = targetDate.plusMonths(1);
		}
		return calendarService.getNextLaboralDay(targetDate.toDate(), 0);
	}
}
