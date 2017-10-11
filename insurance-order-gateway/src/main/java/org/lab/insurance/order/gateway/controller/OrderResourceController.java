package org.lab.insurance.order.gateway.controller;

import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.domain.core.insurance.repository.OrderRepository;
import org.lab.insurance.domain.hateoas.insurance.OrderResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.glasnost.orika.MapperFacade;

@RestController
@RequestMapping(value = "/search")
public class OrderResourceController {

	@Autowired
	private OrderRepository repository;
	@Autowired
	private MapperFacade mapper;

	// TODO configure zuul paths
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<OrderResource> searchById(@PathVariable(value = "id") String id) {
		// TODO handle optional
		Order entity = repository.findById(id).get();
		if (entity == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		OrderResource resource = mapper.map(entity, OrderResource.class);
		// set-up hateoas links
		resource.add(new Link("/api/orders/search" + entity.getId()));
		if (resource.getDistribution() != null) {
			resource.getDistribution()
					.forEach(x -> x.getAsset().add(new Link("/api/assets/search/" + x.getAsset().getId())));
		}
		return new ResponseEntity<>(resource, HttpStatus.OK);
	}
}
