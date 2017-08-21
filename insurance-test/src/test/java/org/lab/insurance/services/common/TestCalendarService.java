package org.lab.insurance.services.common;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.lab.insurance.engine.guice.InsuranceCoreModule;
import org.lab.insurance.model.jpa.common.Holiday;
import org.lab.insurance.model.jpa.common.HolidayCalendar;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;

@Ignore
public class TestCalendarService {

	@Test
	public void test() {
		Injector injector = Guice.createInjector(new InsuranceCoreModule());
		injector.getInstance(PersistService.class).start();
		CalendarService calendarService = injector.getInstance(CalendarService.class);
		EntityManager entityManager = injector.getProvider(EntityManager.class).get();

		{
			Date d0A = new DateTime(2015, 2, 23, 0, 0, 0, 0).toDate();
			Date d0B = calendarService.getNextLaboralDay(d0A, 2);
			Assert.assertEquals(new DateTime(2015, 2, 25, 0, 0, 0, 0).toDate(), d0B);
		}

		{
			Date d1A = new DateTime(2015, 2, 23, 0, 0, 0, 0).toDate();
			Date d1B = calendarService.getNextLaboralDay(d1A, 0);
			Assert.assertEquals(new DateTime(2015, 2, 23, 0, 0, 0, 0).toDate(), d1B);
		}

		{
			Date d2A = new DateTime(2015, 2, 25, 0, 0, 0, 0).toDate();
			Date d2B = calendarService.getNextLaboralDay(d2A, -2);
			Assert.assertEquals(new DateTime(2015, 2, 23, 0, 0, 0, 0).toDate(), d2B);
		}

		// Holidays
		Date holiday = new DateTime(2015, 1, 6, 0, 0, 0, 0).toDate();
		HolidayCalendar calendar = null;
		if (!calendarService.isHoliday(holiday)) {
			try {
				TypedQuery<HolidayCalendar> query = entityManager.createQuery("select e from HolidayCalendar e where e.name = :name", HolidayCalendar.class);
				calendar = query.setParameter("name", "COMMON").getSingleResult();
			} catch (NoResultException ex) {
				entityManager.getTransaction().begin();
				calendar = new HolidayCalendar();
				calendar.setName("COMMON");
				entityManager.persist(calendar);
				entityManager.getTransaction().commit();
			}
			entityManager.getTransaction().begin();
			Holiday holidayEntity = new Holiday();
			holidayEntity.setCalendar(calendar);
			holidayEntity.setName("Reyes Magos");
			holidayEntity.setHolidayDate(holiday);
			entityManager.persist(holidayEntity);
			entityManager.getTransaction().commit();
		} else {
			TypedQuery<HolidayCalendar> query = entityManager.createQuery("select e from HolidayCalendar e where e.name = :name", HolidayCalendar.class);
			calendar = query.setParameter("name", "COMMON").getSingleResult();
		}

		{
			Assert.assertTrue(calendarService.isHoliday(holiday));
			Date d3A = new DateTime(2015, 1, 5, 0, 0, 0, 0).toDate();
			Date d3B = calendarService.getNextLaboralDay(d3A, 2);
			Assert.assertEquals(new DateTime(2015, 1, 8, 0, 0, 0, 0).toDate(), d3B);
		}

		{
			Date d4A = new DateTime(2015, 1, 5, 0, 0, 0, 0).toDate();
			Date d4B = calendarService.getNextLaboralDay(d4A, 2, Arrays.asList(calendar));
			Assert.assertEquals(new DateTime(2015, 1, 8, 0, 0, 0, 0).toDate(), d4B);
		}
	}
}
