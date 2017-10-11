package org.lab.insurance.portfolio.gateway.controller;

import org.lab.insurance.domain.core.portfolio.Portfolio;
import org.lab.insurance.domain.core.portfolio.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "init")
public class PortfolioController {

	@Autowired
	private PortfolioRepository repo;

	@RequestMapping("/{id}")
	public ResponseEntity<Portfolio> find(@PathVariable(value = "id") String id) {
		// TODO
		return new ResponseEntity<>(repo.findById(id).get(), HttpStatus.OK);
	}

}
