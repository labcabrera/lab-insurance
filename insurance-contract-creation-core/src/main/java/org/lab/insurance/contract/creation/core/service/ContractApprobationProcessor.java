package org.lab.insurance.contract.creation.core.service;

import org.lab.insurance.common.exception.InsuranceException;
import org.lab.insurance.common.services.StateMachineService;
import org.lab.insurance.common.services.TimestampProvider;
import org.lab.insurance.domain.action.contract.ContractApprobation;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.contract.ContractDates;
import org.lab.insurance.domain.core.contract.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

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

	public Contract process(ContractApprobation request) {
		log.debug("Processing contract approbation {}", request);
		Assert.notNull(request, "Missing request");
		Assert.notNull(request.getContractId(), "Missing contractId");
		String contractId = request.getContractId();

		Contract contract = contractRepo.findById(contractId)
				.orElseThrow(() -> new InsuranceException("Unknow contract " + contractId));

		if (contract.getDates() == null) {
			contract.setDates(new ContractDates());
		}
		contract.getDates().setStartDate(timestampProvider.getCurrentDate());
		stateMachineService.createTransition(contract, Contract.States.APPROVED.name(), false);
		contractRepo.save(contract);
		// TODO
		return contract;
	}

}
