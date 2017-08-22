package org.lab.insurance.ms.contract.creation.controllers;

import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.ms.contract.creation.domain.ContractCreateInfo;
import org.lab.insurance.ms.contract.creation.domain.ContractPrepareInfo;
import org.lab.insurance.ms.contract.creation.service.ContractCreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/contract-creation")
public class ContractCreationController {

	@Autowired
	private ContractCreationService contractCreationService;

	@RequestMapping(value = "/prepare", method = RequestMethod.POST)
	public ContractCreateInfo prepare(ContractPrepareInfo request) {
		return contractCreationService.prepare(request);
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Contract save(ContractCreateInfo request) {
		return contractCreationService.save(request);
	}

}
