package com.lab.insurance.contract.creation.gateway.controller;

import org.lab.insurance.contract.creation.integration.domain.ContractCreationData;
import org.lab.insurance.contract.creation.integration.domain.ContractPrepareInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/api/v1/contract/creation/")
public class ContractPrepareController {

	@RequestMapping(value = "/prepare", method = RequestMethod.POST)
	@ResponseBody
	public ContractCreationData prepare(ContractPrepareInfo data) {
		ContractCreationData result = new ContractCreationData();
		// TODO
		return result;
	}

}
