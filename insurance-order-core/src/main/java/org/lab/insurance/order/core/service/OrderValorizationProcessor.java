package org.lab.insurance.order.core.service;

import org.lab.insurance.domain.core.insurance.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderValorizationProcessor {

	public Order process(Order request) {
		return request;
	}

}
