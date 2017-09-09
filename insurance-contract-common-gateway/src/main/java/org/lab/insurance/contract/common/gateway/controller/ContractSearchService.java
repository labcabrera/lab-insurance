package org.lab.insurance.contract.common.gateway.controller;

import java.util.ArrayList;

import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.contract.repository.ContractRepository;
import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.domain.hateoas.contract.ContractResource;
import org.lab.insurance.domain.hateoas.insurance.OrderResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class ContractSearchService {

	@Autowired
	private ContractRepository contractRepo;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<ContractResource> findById(String id) {
		Contract entity = contractRepo.findOne(id);
		if (entity == null) {
			new ResponseEntity<>(entity, HttpStatus.NOT_FOUND);
		}

		ContractResource resource = new ContractResource();
		resource.add(ControllerLinkBuilder.linkTo(ContractSearchService.class).slash(entity.getId()).withSelfRel());

		resource.setContractId(entity.getId());
		resource.setDates(entity.getDates());
		resource.setCurrentState(entity.getCurrentState());
		resource.setOrders(new ArrayList<>());

		// TODO
		resource.setOrders(new ArrayList<OrderResource>());
		for (Order i : entity.getOrders()) {
			OrderResource order = new OrderResource();
			// TODO revisar como hacerlo para que pueda ir via zuul y via directa
			order.add(new Link("/api/orders/search/" + i.getId()));
			order.setOrderId(i.getId());
			order.setType(i.getType());
			resource.getOrders().add(order);
		}

		return new ResponseEntity<>(resource, HttpStatus.OK);
	}

}
