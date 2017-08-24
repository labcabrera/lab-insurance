package org.lab.insurance.ms.contract.creation.controllers;

import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.ms.contract.creation.domain.ContractCreationData;
import org.lab.insurance.ms.contract.creation.domain.ContractPrepareInfo;
import org.lab.insurance.ms.contract.creation.integration.gateway.ContractCreationGateway;
import org.lab.insurance.ms.contract.creation.service.ContractPrepareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/contract-creation")
public class ContractCreationController {

	@Autowired
	private ContractPrepareService contractCreationService;
	@Autowired
	private ContractCreationGateway gateway;

	@RequestMapping(value = "/prepare", method = RequestMethod.POST)
	public ContractCreationData prepare(ContractPrepareInfo request) {
		return contractCreationService.prepare(request);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Contract save(ContractCreationData request) {
		return gateway.process(request);
	}

}
