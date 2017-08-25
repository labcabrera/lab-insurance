package com.lab.insurance.contract.creation.gateway.messaging;

import org.lab.insurance.contract.creation.integration.IntegrationConstants.Channels;
import org.lab.insurance.contract.creation.integration.domain.ContractCreationData;
import org.lab.insurance.model.contract.Contract;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface ContractCreationGateway {

	@Gateway(requestChannel = Channels.ContractRequest, replyChannel = Channels.ContractResponse)
	Contract process(ContractCreationData data);

}
