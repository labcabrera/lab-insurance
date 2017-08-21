package org.lab.insurance.services.common;

import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.junit.Test;

public class TestTimestampProvider {

	@Test
	public void test() throws InterruptedException {
		TimestampProvider timestampProvider = new TimestampProvider();
		Date fakeDate = new DateTime(2015, 1, 1, 9, 30, 30, 0).toDate();
		timestampProvider.setDate(fakeDate);
		Thread.sleep(1000);
		Date currentDate = timestampProvider.getCurrentDateTime();
		System.out.println("Fake date: " + DateFormatUtils.ISO_DATETIME_FORMAT.format(currentDate));
		timestampProvider.resetDate();
		currentDate = timestampProvider.getCurrentDateTime();
		System.out.println("Real date: " + DateFormatUtils.ISO_DATETIME_FORMAT.format(currentDate));
	}
}
