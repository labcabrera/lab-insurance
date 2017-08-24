package org.lab.insurance.ms.contract.creation.integration.endpoint;

import java.util.ArrayList;

import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.ms.contract.creation.domain.ContractCreationData;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class ContractProcessActivator {

	//@ServiceActivator(inputChannel = Channels.ContractCreationProcess)
	public Contract getResponse(Message<ContractCreationData> msg) {
		System.out.println(">> activator process " + msg.getPayload());
		ContractCreationData data = msg.getPayload();
		Contract contract = new Contract();
		contract.setAgreement(data.getAgreement());
		contract.setOrders(new ArrayList<>());
		contract.getOrders().add(data.getInitialPayment());
		contract.setRelations(data.getRelations());
		contract.setNumber("12345"); //TODO
		return contract;
	}
}
