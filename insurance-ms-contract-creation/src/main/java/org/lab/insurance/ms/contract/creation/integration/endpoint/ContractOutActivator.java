package org.lab.insurance.ms.contract.creation.integration.endpoint;

import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.contract.ContractPersonRelation;
import org.lab.insurance.model.contract.repository.ContractPersonRelationRepository;
import org.lab.insurance.model.contract.repository.ContractRepository;
import org.lab.insurance.model.insurance.BaseAsset;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.insurance.OrderDistribution;
import org.lab.insurance.model.insurance.repository.BaseAssetRepository;
import org.lab.insurance.model.insurance.repository.OrderRepository;
import org.lab.insurance.model.legalentity.Person;
import org.lab.insurance.model.legalentity.repository.PersonRepository;
import org.lab.insurance.model.product.AgreementRepository;
import org.lab.insurance.ms.contract.creation.integration.IntegrationConstants.Channels;
import org.lab.insurance.ms.contract.creation.service.ContractNumberGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class ContractOutActivator {

	@Autowired
	private ContractRepository contractRepository;
	@Autowired
	private ContractNumberGenerator contractNumberGenerator;
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private ContractPersonRelationRepository policyEntityRelationRepository;
	@Autowired
	private BaseAssetRepository baseAssetRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private AgreementRepository agreementRepository;

	// TODO separar esta logica en diferentes procesadores
	@ServiceActivator(inputChannel = Channels.ContractCreationOut)
	public Contract getResponse(Message<Contract> msg) {
		Contract request = msg.getPayload();
		Contract result = new Contract();

		// primero creamos el contrato vacio
		contractNumberGenerator.generate(result);
		result.setAgreement(agreementRepository.findByCode(request.getAgreement().getCode()));
		contractRepository.save(result);

		// guardamos las relaciones con la referencia del contrato
		for (ContractPersonRelation i : request.getRelations()) {
			Person effectivePerson = resolvePersonFromRelation(i);
			i.setPerson(effectivePerson);
			i.setContract(result);
			policyEntityRelationRepository.save(i);
		}
		result.setRelations(request.getRelations());

		// guardamos las ordenes con la referencia del contraro
		for (Order order : request.getOrders()) {
			order.setContract(result);
			for (OrderDistribution i : order.getBuyDistribution()) {
				if (i.getAsset().getId() == null) {
					BaseAsset asset = baseAssetRepository.findByIsin(i.getAsset().getIsin());
					i.setAsset(asset);
				}
			}
			orderRepository.save(order);
		}
		result.setOrders(request.getOrders());

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
