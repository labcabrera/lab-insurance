package org.lab.insurance.portfolio.gateway.controller;

import org.lab.insurance.portfolio.core.domain.ContractPortfolioRelation;
import org.lab.insurance.portfolio.gateway.integration.ContractCreationGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "init")
public class PortfolioInitializationController {

	@Autowired
	private ContractCreationGateway gateway;

	@RequestMapping("/{contractId}")
	public ContractPortfolioRelation initialize(@PathVariable(value = "contractId") String contractId) {
		return gateway.process(contractId);
	}

}