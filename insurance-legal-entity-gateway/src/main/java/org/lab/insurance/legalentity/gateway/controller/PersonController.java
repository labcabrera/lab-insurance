package org.lab.insurance.legalentity.gateway.controller;

import java.util.List;

import org.lab.insurance.domain.core.legalentity.Person;
import org.lab.insurance.domain.core.legalentity.repository.PersonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/persons")
@RestController
public class PersonController {

	private PersonRepository repository;

	// TODO search & pagination
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Person> search() {
		return repository.findAll();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Person> searchById(@PathVariable(value = "id") String id) {
		Person entity = repository.findOne(id);
		HttpStatus status = entity != null ? HttpStatus.OK : HttpStatus.NOT_FOUND;
		return new ResponseEntity<>(entity, status);
	}

}
