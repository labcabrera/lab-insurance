package com.lab.insurance.contract.creation.gateway.controller;

import org.lab.insurance.contract.creation.core.domain.ContractPrepareData;
import org.lab.insurance.domain.action.contract.ContractCreation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/api/v1/contract/creation/")
public class ContractPrepareController {

	@RequestMapping(value = "/prepare", method = RequestMethod.POST)
	@ResponseBody
	public ContractCreation prepare(ContractPrepareData data) {
		ContractCreation result = new ContractCreation();
		// TODO
		return result;
	}

}
