package org.lab.insurance.services.common;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.lab.insurance.model.common.HolidayCalendar;

public class CalendarService {

	/**
	 * Devuelve el siguiente dia festivo (o anterior si days es menor que cero) pasados una cantidad dada de dias.
	 * 
	 * @param date
	 * @param days
	 * @return
	 */
	public Date getNextLaboralDay(Date date, int days) {
		return getNextLaboralDay(date, days, null);
	}

	/**
	 * Devuelve el siguiente dia festivo (o anterior si days es menor que cero) pasados una cantidad dada de dias. Nos
	 * permite seleccionar una lista de calendarios de vacaciones personalizada (por ejemplo podemos tener varios
	 * calendarios para diferentes paises y solo querer usar el calendario de uno determinado).
	 * 
	 * @param date
	 * @param days
	 * @param calendars
	 * @return
	 */
	public Date getNextLaboralDay(Date date, int days, List<HolidayCalendar> calendars) {
		DateTime check = new DateTime(date);
		boolean negative = days < 0;
		int count = Math.abs(days);
		while (count > 0) {
			check = negative ? check.minusDays(1) : check.plusDays(1);
			if (check.getDayOfWeek() == 6 || check.getDayOfWeek() == 7 || isHoliday(check.toDate(), calendars)) {
				continue;
			}
			count--;
		}
		return check.toDate();
	}

	public boolean isHoliday(Date value) {
		return isHoliday(value, null);
	}

	public boolean isHoliday(Date value, List<HolidayCalendar> calendars) {
		throw new RuntimeException("Not implemented jpa -> mongo");
		// EntityManager entityManager = entityManagerProvider.get();
		// TypedQuery<Long> query;
		// if (calendars == null || calendars.isEmpty()) {
		// query = entityManager.createNamedQuery("Holiday.countByDate", Long.class).setParameter("value", value);
		// }
		// else {
		// query = entityManager.createNamedQuery("Holiday.countByDateInCalendars", Long.class)
		// .setParameter("value", value).setParameter("calendars", calendars);
		// }
		// return query.getSingleResult() > 0L;
	}
}
