package org.lab.insurance.ms.contract.search;

import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.contract.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/contracts")
public class ContractSearchService {

	@Autowired
	private ContractRepository repository;

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
