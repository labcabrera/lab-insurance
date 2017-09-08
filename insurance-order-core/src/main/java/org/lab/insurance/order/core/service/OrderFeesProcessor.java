package org.lab.insurance.order.core.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.lab.insurance.domain.insurance.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderFeesProcessor {

	public Order process(Order order) {
		log.debug("Processing fees in order {}", order);
		BigDecimal percent = new BigDecimal("1");
		BigDecimal grossAmount = order.getGrossAmount();
		BigDecimal feesAmount = grossAmount.multiply(percent).divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN);
		BigDecimal netAmount = grossAmount.subtract(feesAmount);
		order.setNetAmount(netAmount);
		return order;
	}
}
