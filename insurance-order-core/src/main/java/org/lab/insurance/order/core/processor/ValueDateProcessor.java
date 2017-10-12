package org.lab.insurance.order.core.processor;

import java.util.Date;

import org.lab.insurance.common.services.calendar.CalendarService;
import org.lab.insurance.domain.action.contract.OrderValorizationAgreementInfo;
import org.lab.insurance.domain.core.insurance.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ValueDateProcessor {

	@Autowired
	private CalendarService calendarService;

	public Order process(Order request, OrderValorizationAgreementInfo metadata) {
		Assert.notNull(request.getDates(), "Missing order dates");
		Assert.notNull(request.getDates().getEffective(), "Missing order effective");
		Assert.isNull(request.getDates().getValueDate(), "Unexpected value date");
		Date effective = request.getDates().getEffective();
		Date valueDate = calendarService.getNextLaboralDay(effective, metadata.getValorizationOffset(),
				metadata.getCalendar());
		request.getDates().setValueDate(valueDate);
		return request;
	}

	// TODO remove this method
	public Order process(Order request) {
		OrderValorizationAgreementInfo metadata = new OrderValorizationAgreementInfo();
		metadata.setCalendar(null);
		metadata.setValorizationOffset(2);

		return process(request, metadata);
	}

}
