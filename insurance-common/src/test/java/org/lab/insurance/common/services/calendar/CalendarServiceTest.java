package org.lab.insurance.common.services.calendar;

import java.util.Date;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

public class CalendarServiceTest {

	@Test
	public void test01() {
		CalendarService service = new CalendarService();
		DateTime dt = new DateTime(2017, 10, 16, 0, 0, 0);
		Date result = service.getNextLaboralDay(dt.toDate(), 2, null);
		DateTime dtResult = new DateTime(result);

		Assert.assertEquals(2017, dtResult.getYear());
		Assert.assertEquals(10, dtResult.getMonthOfYear());
		Assert.assertEquals(18, dtResult.getDayOfMonth());
	}

	@Test
	public void test02() {
		CalendarService service = new CalendarService();
		DateTime dt = new DateTime(2017, 10, 1, 0, 0, 0);
		Date result = service.getNextLaboralDay(dt.toDate(), 0, null);
		DateTime dtResult = new DateTime(result);

		Assert.assertEquals(2017, dtResult.getYear());
		Assert.assertEquals(10, dtResult.getMonthOfYear());
		Assert.assertEquals(2, dtResult.getDayOfMonth());
	}

	@Test
	public void test03() {
		CalendarService service = new CalendarService();
		DateTime dt = new DateTime(2017, 10, 29, 0, 0, 0);
		Date result = service.getNextLaboralDay(dt.toDate(), -2, null);
		DateTime dtResult = new DateTime(result);

		Assert.assertEquals(2017, dtResult.getYear());
		Assert.assertEquals(10, dtResult.getMonthOfYear());
		Assert.assertEquals(26, dtResult.getDayOfMonth());
	}

}
