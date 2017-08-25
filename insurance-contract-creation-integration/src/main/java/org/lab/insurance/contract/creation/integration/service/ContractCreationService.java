package org.lab.insurance.contract.creation.integration.service;

import java.util.ArrayList;

import org.lab.insurance.contract.creation.integration.domain.ContractCreationData;
import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.contract.ContractPersonRelation;
import org.lab.insurance.model.contract.repository.ContractPersonRelationRepository;
import org.lab.insurance.model.contract.repository.ContractRepository;
import org.lab.insurance.model.insurance.BaseAsset;
import org.lab.insurance.model.insurance.OrderDistribution;
import org.lab.insurance.model.insurance.repository.BaseAssetRepository;
import org.lab.insurance.model.insurance.repository.OrderRepository;
import org.lab.insurance.model.legalentity.Person;
import org.lab.insurance.model.legalentity.repository.PersonRepository;
import org.lab.insurance.model.product.AgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContractCreationService {

	@Autowired
	private ContractRepository contractRepository;
	@Autowired
	private AgreementRepository agreementRepository;
	@Autowired
	private ContractPersonRelationRepository contractPersonRelationRepository;
	@Autowired
	private BaseAssetRepository baseAssetRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PersonRepository personRepository;

	public Contract process(ContractCreationData data) {
		log.info("Processing contract creation {}", data);

		Contract result = new Contract();

		// primero creamos el contrato vacio
		result.setAgreement(agreementRepository.findByCode(data.getAgreementCode()));
		contractRepository.save(result);

		// guardamos las relaciones con la referencia del contrato
		for (ContractPersonRelation i : data.getRelations()) {
			Person effectivePerson = resolvePersonFromRelation(i);
			i.setPerson(effectivePerson);
			i.setContract(result);
			contractPersonRelationRepository.save(i);
		}
		result.setRelations(data.getRelations());

		// guardamos las ordenes con la referencia del contraro
		for (OrderDistribution i : data.getInitialPayment().getBuyDistribution()) {
			if (i.getAsset().getId() == null) {
				BaseAsset asset = baseAssetRepository.findByIsin(i.getAsset().getIsin());
				i.setAsset(asset);

			}
		}
		orderRepository.save(data.getInitialPayment());
		result.setOrders(new ArrayList<>());
		result.getOrders().add(data.getInitialPayment());

		contractRepository.save(result);
		return result;

	}

	// TODO utilizar un servicio para resolver esto
	private Person resolvePersonFromRelation(ContractPersonRelation relation) {
		Person entity = relation.getPerson();
		if (entity.getId() == null) {
			if (entity.getIdCard() != null && entity.getIdCard().getNumber() != null) {
				Person tmp = personRepository.findByIdCardNumber(entity.getIdCard().getNumber());
				Assert.notNull(tmp, "Cant resolve legal entity " + entity);
				return tmp;
			} else {
				throw new RuntimeException("Cant resolve legal entity");
			}
		} else {
			return relation.getPerson();
		}
	}

}
