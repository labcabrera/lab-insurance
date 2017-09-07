package org.lab.insurance.contract.creation.core.service;

import org.lab.insurance.domain.contract.Contract;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ContractApprobationProcessor {

	public Contract process(Contract contract) {
		log.debug("Processing contract approbation {}", contract);
		// TODO
		return contract;
	}

}
