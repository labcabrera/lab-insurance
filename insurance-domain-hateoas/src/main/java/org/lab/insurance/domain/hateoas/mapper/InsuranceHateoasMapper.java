package org.lab.insurance.domain.hateoas.mapper;

import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.domain.hateoas.insurance.OrderResource;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

public class InsuranceHateoasMapper extends ConfigurableMapper {

	@Override
	protected void configure(MapperFactory factory) {
		factory.classMap(Order.class, OrderResource.class).customize(new OrderResourceMapper()).byDefault().register();
	}

}
