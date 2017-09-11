package org.lab.insurance.domain.hateoas.insurance;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.domain.hateoas.config.InsuranceHateoasConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import ma.glasnost.orika.MapperFacade;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = InsuranceHateoasConfig.class)
public class OrderResourceMappingTest {

	@Autowired
	private MapperFacade mapper;

	@Test
	public void test() {
		Order order = new Order();
		order.setId("orderId");

		OrderResource resource = mapper.map(order, OrderResource.class);

		Assert.assertEquals(order.getId(), resource.getOrderId());
	}

}
