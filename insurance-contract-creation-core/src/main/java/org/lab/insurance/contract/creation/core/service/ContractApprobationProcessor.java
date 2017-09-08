package org.lab.insurance.contract.creation.core.service;

import org.lab.insurance.common.services.StateMachineService;
import org.lab.insurance.common.services.TimestampProvider;
import org.lab.insurance.domain.contract.Contract;
import org.lab.insurance.domain.contract.ContractDates;
import org.lab.insurance.domain.contract.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ContractApprobationProcessor {

	@Autowired
	private ContractRepository contractRepo;
	@Autowired
	private TimestampProvider timestampProvider;
	@Autowired
	private StateMachineService stateMachineService;

	public Contract process(Contract contract) {
		log.debug("Processing contract approbation {}", contract);
		if (contract.getDates() == null) {
			contract.setDates(new ContractDates());
		}
		contract.getDates().setStartDate(timestampProvider.getCurrentDate());
		stateMachineService.createTransition(contract, Contract.States.APPROBED.name(), false);
		contractRepo.save(contract);
		// TODO
		return contract;
	}

}
