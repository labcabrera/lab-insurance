package org.lab.insurance.common.services.calendar;

import java.util.Date;

import org.joda.time.DateTime;
import org.lab.insurance.domain.core.common.HolidayCalendar;
import org.springframework.stereotype.Component;

@Component
public class CalendarService {

	public Date getNextLaboralDay(final Date date, final int days, final HolidayCalendar calendar) {
		DateTime tmp = new DateTime(date);
		// Note: in the case of previous dates we want to have an offset
		int check = days < 0 ? Math.abs(days) - 1 : days;
		int count = 0;
		int increment = days < 0 ? -1 : 1;
		do {
			if (!isLaboralDay(tmp.toDate(), calendar)) {
				tmp = tmp.plusDays(increment);
				continue;
			}
			tmp = tmp.plusDays(increment);
			count++;
		}
		while (count < check);
		return tmp.toDate();
	}

	public boolean isLaboralDay(Date date, HolidayCalendar calendar) {
		DateTime dt = new DateTime(date);
		if (dt.getDayOfWeek() > 5) {
			return false;
		}
		// TODO check holiday
		return true;
	}
}
