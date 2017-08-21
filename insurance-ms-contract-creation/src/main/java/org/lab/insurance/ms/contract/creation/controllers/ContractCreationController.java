package org.lab.insurance.ms.contract.creation.controllers;

import org.lab.insurance.model.jpa.contract.Contract;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/contract-creation")
public class ContractCreationController {

	@RequestMapping(value = "/prepare")
	public Contract prepare(Contract contract) {
		return contract;
	}

}
