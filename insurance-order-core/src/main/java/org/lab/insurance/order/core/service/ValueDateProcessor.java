package org.lab.insurance.order.core.service;

import org.joda.time.DateTime;
import org.lab.insurance.domain.core.insurance.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ValueDateProcessor {

	public Order process(Order request) {
		Assert.notNull(request.getDates(), "Missing order dates");
		Assert.notNull(request.getDates().getEffective(), "Missing order effective");
		Assert.isNull(request.getDates().getValueDate(), "Unexpected value date");

		// TODO integracion con servicio de calendario
		// TODO integracion con el acuerdo marco

		DateTime effective = new DateTime(request.getDates().getEffective());
		DateTime valueDate = effective.plusDays(2);

		request.getDates().setValueDate(valueDate.toDate());

		return request;
	}

}
