package org.lab.insurance.contract.creation.core.service;

import java.util.ArrayList;
import java.util.Random;

import org.lab.insurance.commons.services.TimestampProvider;
import org.lab.insurance.contract.creation.core.domain.ContractCreationData;
import org.lab.insurance.domain.common.audit.AuditData;
import org.lab.insurance.domain.contract.Contract;
import org.lab.insurance.domain.contract.ContractPersonRelation;
import org.lab.insurance.domain.contract.repository.ContractPersonRelationRepository;
import org.lab.insurance.domain.contract.repository.ContractRepository;
import org.lab.insurance.domain.insurance.Asset;
import org.lab.insurance.domain.insurance.OrderDistribution;
import org.lab.insurance.domain.insurance.repository.AssetRepository;
import org.lab.insurance.domain.insurance.repository.OrderRepository;
import org.lab.insurance.domain.legalentity.Person;
import org.lab.insurance.domain.legalentity.repository.PersonRepository;
import org.lab.insurance.domain.product.AgreementRepository;
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
	private AssetRepository baseAssetRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private TimestampProvider timeStampProvider;

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
				Asset asset = baseAssetRepository.findByIsin(i.getAsset().getIsin());
				i.setAsset(asset);

			}
		}
		orderRepository.save(data.getInitialPayment());
		result.setOrders(new ArrayList<>());
		result.getOrders().add(data.getInitialPayment());

		result.setNumber(String.valueOf(new Random().nextInt(10000)));
		result.setAuditData(AuditData.builder().created(timeStampProvider.getCurrentDate()).build());

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
