package org.lab.insurance.io.service;

import org.lab.insurance.domain.core.contract.Contract;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContractDocumentCreationService {

	public Contract process(Contract contract) {
		log.debug("Creating contract documentation");
		return contract;
	}

}
