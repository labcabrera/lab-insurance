package org.lab.insurance.order.core.service;

import org.apache.commons.lang3.Validate;
import org.lab.insurance.domain.core.insurance.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderValorizationProcessor {

	public Order process(Order request) {
		log.info("Valuing order {}", request);
		Validate.isTrue(Order.States.VALUING.name().equals(request.getCurrentState().getCode()));
		return request;
	}

}
