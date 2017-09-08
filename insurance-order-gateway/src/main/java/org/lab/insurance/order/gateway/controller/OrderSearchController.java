package org.lab.insurance.order.gateway.controller;

import java.util.List;

import org.lab.insurance.domain.insurance.Order;
import org.lab.insurance.domain.insurance.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/orders")
@RestController
public class OrderSearchController {

	@Autowired
	private OrderRepository repository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Order> search() {
		return repository.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Order> searchById(@PathVariable(value = "id") String id) {
		Order entity = repository.findOne(id);
		HttpStatus status = entity != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(entity, status);
	}

}
