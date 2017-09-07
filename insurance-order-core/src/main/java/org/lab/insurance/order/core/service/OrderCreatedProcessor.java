package org.lab.insurance.order.core.service;

import org.lab.insurance.domain.insurance.Order;
import org.lab.insurance.domain.insurance.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderCreatedProcessor {

	@Autowired
	private OrderRepository orderRepo;

	public Order initialize(Order request) {
		log.info("Intializing order {}", request.getId());
		Order order = orderRepo.findOne(request.getId());
		Assert.notNull(order, "Unknow order");
		// TODO
		return order;
	}
}
