package com.lab.insurance.contract.creation.gateway.controller;

import org.lab.insurance.domain.action.contract.ContractCreation;
import org.lab.insurance.domain.core.contract.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.lab.insurance.contract.creation.gateway.integration.ContractCreationGateway;

@RequestMapping(value = "/api/v1/contract/creation/")
@RestController
public class ContractCreateController {

	@Autowired
	private ContractCreationGateway gateway;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public Contract execute(ContractCreation data) {
		Contract result = gateway.processCreation(data);
		return result;
	}
}
