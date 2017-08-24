package org.lab.insurance.ms.contract.creation.integration.endpoint;

import java.util.ArrayList;

import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.ms.contract.creation.domain.ContractCreationData;
import org.lab.insurance.ms.contract.creation.integration.IntegrationConstants.Channels;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ContractInitializationTransformer {

	@Transformer(inputChannel = Channels.ContractCreationIn, outputChannel = Channels.ContractCreationProcess)
	public Contract transform(Message<ContractCreationData> msg) {
		log.debug("Transform");
		ContractCreationData data = msg.getPayload();
		Contract contract = new Contract();
		contract.setAgreement(data.getAgreement());
		contract.setOrders(new ArrayList<>());
		contract.getOrders().add(data.getInitialPayment());
		contract.setRelations(data.getRelations());
		return contract;
	}
}
