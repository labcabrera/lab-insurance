package org.lab.insurance.ms.contract.creation.service;

import org.lab.insurance.model.contract.repository.ContractPersonRelationRepository;
import org.lab.insurance.model.contract.repository.ContractRepository;
import org.lab.insurance.model.legalentity.repository.PersonRepository;
import org.lab.insurance.model.product.Agreement;
import org.lab.insurance.model.product.AgreementRepository;
import org.lab.insurance.ms.contract.creation.domain.ContractCreationData;
import org.lab.insurance.ms.contract.creation.domain.ContractPrepareInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractPrepareService {

	@Autowired
	private AgreementRepository agreementRepository;
	@Autowired
	private ContractRepository contractRepository;
	@Autowired
	private ContractNumberGenerator contractNumberGenerator;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private ContractPersonRelationRepository policyEntityRelationRepository;

	// @Autowired
	// private RabbitTemplate rabbitTemplate;

	public ContractCreationData prepare(ContractPrepareInfo prepareInfo) {
		Agreement agreement = agreementRepository.findByCode(prepareInfo.getAgreementCode());
		ContractCreationData response = new ContractCreationData();
		response.setAgreement(agreement);
		return response;
	}

}
