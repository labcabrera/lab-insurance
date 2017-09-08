package org.lab.insurance.order.core.integration;

import org.apache.commons.lang.StringUtils;
import org.lab.insurance.domain.insurance.Order;
import org.lab.insurance.domain.insurance.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class OrderMongoReader {

	@Autowired
	private OrderRepository orderRepo;

	public Order read(Order request) {
		Assert.isTrue(request != null && StringUtils.isNotBlank(request.getId()), "Missing order identifier");
		Order result = orderRepo.findOne(request.getId());
		Assert.notNull(result, "Unknow order " + request.getId());
		return result;
	}

}
