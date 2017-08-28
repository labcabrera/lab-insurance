package org.lab.insurance.contract.common.gateway.controller;

import java.util.List;

import org.lab.insurance.domain.contract.Contract;
import org.lab.insurance.domain.contract.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contracts")
public class ContractSearchService {

	@Autowired
	private ContractRepository repository;
	@Autowired
	private MongoTemplate mongoTemplate;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Contract> searchAlt(@RequestParam(value = "p", defaultValue = "0") Integer page,
			@RequestParam(value = "s", defaultValue = "10") Integer size) {
		final Pageable pageableRequest = new PageRequest(page, size);
		Query query = new Query();
		query.with(pageableRequest);
		query.with(new Sort(Sort.Direction.ASC, "number"));
		List<Contract> result = mongoTemplate.find(query, Contract.class);
		return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Contract> findById(String id) {
		Contract entity = repository.findOne(id);
		HttpStatus status = entity != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<Contract>(entity, status);
	}

	@RequestMapping(value = "/number/{id}", method = RequestMethod.GET)
	public ResponseEntity<Contract> findByNumber(String number) {
		Contract entity = repository.findByNumber(number);
		HttpStatus status = entity != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<Contract>(entity, status);
	}

}
