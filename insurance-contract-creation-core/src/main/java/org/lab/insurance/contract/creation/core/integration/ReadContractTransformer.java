package org.lab.insurance.contract.creation.core.integration;

import org.lab.insurance.common.exception.InsuranceException;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.contract.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.transformer.GenericTransformer;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReadContractTransformer implements GenericTransformer<Contract, Contract> {

	@Autowired
	private ContractRepository contractRepo;

	@Override
	public Contract transform(Contract source) {
		log.debug("Reading contract {}", source);
		Assert.notNull(source, "Missing contract");
		Assert.notNull(source.getId(), "Missing contract id");
		Contract result = contractRepo.findById(source.getId())
				.orElseThrow(() -> new InsuranceException("Unknow contract " + source.getId()));
		return result;
	}
}