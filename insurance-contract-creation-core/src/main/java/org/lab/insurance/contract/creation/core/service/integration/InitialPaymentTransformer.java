package org.lab.insurance.contract.creation.core.service.integration;

import java.util.List;
import java.util.stream.Collectors;

import org.lab.insurance.domain.contract.Contract;
import org.lab.insurance.domain.insurance.Order;
import org.lab.insurance.domain.insurance.OrderType;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InitialPaymentTransformer implements GenericTransformer<Contract, Order> {

	@Override
	public Order transform(Contract source) {
		log.debug("Transforming entity {}", source);
		List<Order> check = source.getOrders().stream().filter(f -> OrderType.INITIAL_PAYMENT == f.getType())
				.collect(Collectors.toList());
		Assert.isTrue(check.size() == 1, "Invalid order list");
		return check.iterator().next();
	}

}
