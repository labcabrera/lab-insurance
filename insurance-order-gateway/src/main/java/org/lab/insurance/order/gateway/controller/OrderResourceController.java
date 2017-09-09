package org.lab.insurance.order.gateway.controller;

import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.domain.core.insurance.repository.OrderRepository;
import org.lab.insurance.order.gateway.domain.OrderResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/hm/search")
public class OrderResourceController {

	@Autowired
	private OrderRepository repository;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<OrderResource> searchById(@PathVariable(value = "id") String id) {
		Order entity = repository.findOne(id);
		if (entity == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		OrderResource resource = new OrderResource();
		resource.add(ControllerLinkBuilder.linkTo(OrderResourceController.class).slash(entity.getId()).withSelfRel());

		// TODO orika mapper
		resource.setType(entity.getType());
		resource.setOrderId(entity.getContract().getId());
		resource.setBuyDistribution(entity.getBuyDistribution());
		resource.setSellDistribution(entity.getSellDistribution());
		resource.setDates(entity.getDates());
		resource.setNetAmount(entity.getNetAmount());
		resource.setGrossAmount(entity.getGrossAmount());

		return new ResponseEntity<>(resource, HttpStatus.OK);
	}
}
