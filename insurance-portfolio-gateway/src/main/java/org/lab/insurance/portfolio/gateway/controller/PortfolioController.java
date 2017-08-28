package org.lab.insurance.portfolio.gateway.controller;

import org.lab.insurance.domain.portfolio.Portfolio;
import org.lab.insurance.domain.portfolio.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "init")
public class PortfolioController {

	@Autowired
	private PortfolioRepository repo;

	@RequestMapping("/{id}")
	public Portfolio find(@PathVariable(value = "id") String id) {
		return repo.findOne(id);
	}

}
