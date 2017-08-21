package org.lab.insurance.engine;

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.lab.insurance.services.common.TimestampProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DailyActionExecutionRunner {

	private static final Logger LOG = LoggerFactory.getLogger(DailyActionExecutionRunner.class);

	@Inject
	private TimestampProvider timestampProvider;
	@Inject
	private ActionExecutionRunner actionExecutionRunner;

	public void run(Date from, Date to) {
		DateTime tmp = new DateTime(from).withTimeAtStartOfDay();
		DateTime end = new DateTime(to).withTimeAtStartOfDay();
		while (tmp.isBefore(end) || tmp.isEqual(end)) {
			LOG.debug("Executing daily job {}", DateFormatUtils.ISO_DATE_FORMAT.format(tmp.toDate()));
			timestampProvider.setDate(tmp.toDate());
			actionExecutionRunner.run(from, tmp.toDate());
			tmp = tmp.plusDays(1);
		}
	}
}
