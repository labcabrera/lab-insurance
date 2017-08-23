package org.lab.insurance.ms.contract.creation.service;

import java.util.ArrayList;
import java.util.List;

import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.contract.ContractPersonRelation;
import org.lab.insurance.model.contract.repository.ContractPersonRelationRepository;
import org.lab.insurance.model.contract.repository.ContractRepository;
import org.lab.insurance.model.legalentity.Person;
import org.lab.insurance.model.legalentity.repository.PersonRepository;
import org.lab.insurance.model.product.Agreement;
import org.lab.insurance.model.product.AgreementRepository;
import org.lab.insurance.ms.contract.creation.domain.ContractCreateInfo;
import org.lab.insurance.ms.contract.creation.domain.ContractPrepareInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ContractCreationService {

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

	public ContractCreateInfo prepare(ContractPrepareInfo prepareInfo) {
		Agreement agreement = agreementRepository.findByCode(prepareInfo.getAgreementCode());
		ContractCreateInfo response = new ContractCreateInfo();
		response.setAgreement(agreement);
		return response;
	}

	// TODO spring integration gateway
	public Contract save(ContractCreateInfo request) {
		Contract contract = new Contract();
		contract.setAgreement(request.getAgreement());
		contractRepository.save(contract);

		List<ContractPersonRelation> effectiveRelations = new ArrayList<>();
		for (ContractPersonRelation i : request.getRelations()) {
			Person effectivePerson = resolvePersonFromRelation(i);
			i.setPerson(effectivePerson);
			i.setContract(contract);
			policyEntityRelationRepository.save(i);
			effectiveRelations.add(i);
		}
		contract.setRelations(effectiveRelations);
		contractNumberGenerator.generate(contract);
		contractRepository.save(contract);

		// rabbitTemplate.convertAndSend(AmqpConfig.TOPIC_NAME, AmqpConfig.ROUTING_KEY, contract);

		return contract;
	}

	// TODO utilizar un servicio para resolver esto
	public Person resolvePersonFromRelation(ContractPersonRelation relation) {
		Person entity = relation.getPerson();
		if (entity.getId() == null) {
			if (entity.getIdCard() != null && entity.getIdCard().getNumber() != null) {
				Person tmp = personRepository.findByIdCardNumber(entity.getIdCard().getNumber());
				Assert.notNull(tmp, "Cant resolve legal entity " + entity);
				return tmp;
			}
			else {
				throw new RuntimeException("Cant resolve legal entity");
			}
		}
		else {
			return relation.getPerson();
		}
	}

}
