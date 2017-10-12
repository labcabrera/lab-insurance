package org.lab.insurance.order.core.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.lab.insurance.core.math.BigMath;
import org.lab.insurance.domain.core.insurance.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderFeesProcessor {

	public Order process(Order order) {
		log.debug("Processing fees in order {}", order);
		// TODO leer del am
		// TODO caso en el que se defina el netAmount en lugar del grossAmount
		BigDecimal feesPercent = new BigDecimal("1");
		if (BigMath.isNotZero(order.getGrossAmount())) {
			BigDecimal grossAmount = order.getGrossAmount();
			BigDecimal feesAmount = grossAmount.multiply(feesPercent).divide(new BigDecimal("100"), 2,
					RoundingMode.HALF_EVEN);
			BigDecimal netAmount = grossAmount.subtract(feesAmount);
			order.setNetAmount(netAmount);
		}
		else {
			throw new RuntimeException("Not implemented: net amount buy");
		}
		return order;
	}
}
