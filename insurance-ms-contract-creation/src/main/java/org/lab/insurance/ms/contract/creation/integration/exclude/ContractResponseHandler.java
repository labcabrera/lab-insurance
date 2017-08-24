package org.lab.insurance.ms.contract.creation.integration.exclude;

import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.ms.contract.creation.integration.IntegrationConstants;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ContractResponseHandler {

	@ServiceActivator(inputChannel = IntegrationConstants.Channels.ContractCreationOut)
	public Contract getResponse(Message<Contract> msg) {
		log.info("Contract received (response >> .): {}", msg.getPayload());

		Contract contract = msg.getPayload();
		contract.setNumber("12345");
		return contract;
	}
}
