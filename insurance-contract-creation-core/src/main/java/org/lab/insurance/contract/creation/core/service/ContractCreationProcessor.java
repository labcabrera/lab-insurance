package org.lab.insurance.contract.creation.core.service;

import java.util.ArrayList;
import java.util.Random;

import org.lab.insurance.common.services.StateMachineService;
import org.lab.insurance.common.services.TimestampProvider;
import org.lab.insurance.domain.action.contract.ContractCreation;
import org.lab.insurance.domain.core.common.audit.InsuranceAuditData;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.contract.ContractPersonRelation;
import org.lab.insurance.domain.core.contract.repository.ContractPersonRelationRepository;
import org.lab.insurance.domain.core.contract.repository.ContractRepository;
import org.lab.insurance.domain.core.insurance.Asset;
import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.domain.core.insurance.OrderDistribution;
import org.lab.insurance.domain.core.insurance.repository.AssetRepository;
import org.lab.insurance.domain.core.insurance.repository.OrderRepository;
import org.lab.insurance.domain.core.legalentity.Person;
import org.lab.insurance.domain.core.legalentity.repository.PersonRepository;
import org.lab.insurance.domain.core.product.repository.AgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ContractCreationProcessor {

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
	@Autowired
	private StateMachineService stateMachineService;

	public Contract process(ContractCreation data) {
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
		stateMachineService.createTransition(data.getInitialPayment(), Order.States.INITIAL.name(), false);
		orderRepository.save(data.getInitialPayment());
		result.setOrders(new ArrayList<>());
		result.getOrders().add(data.getInitialPayment());

		result.setNumber(String.valueOf(new Random().nextInt(10000)));
		result.setAuditData(InsuranceAuditData.builder().created(timeStampProvider.getCurrentDate()).build());

		stateMachineService.createTransition(result, Contract.States.INITIAL.name(), false);
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
