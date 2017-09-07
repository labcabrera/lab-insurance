package com.lab.insurance.contract.creation.gateway.integration;

import org.lab.insurance.domain.IntegrationConstants.Channels;
import org.lab.insurance.domain.contract.Contract;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface ContractApprobationGateway {

	@Gateway(requestChannel = Channels.ContractApprobationRequest, replyChannel = Channels.ContractApprobationResponse)
	Contract process(Contract data);

}
