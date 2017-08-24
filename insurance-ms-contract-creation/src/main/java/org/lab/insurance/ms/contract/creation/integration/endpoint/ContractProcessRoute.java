package org.lab.insurance.ms.contract.creation.integration.endpoint;

import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.ms.contract.creation.integration.IntegrationConstants.Channels;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class ContractProcessRoute {

	@Router(inputChannel = Channels.ContractCreationProcess)
	public String filter(Message<Contract> msg) {
		return Channels.ContractCreationOut;
	}
}
