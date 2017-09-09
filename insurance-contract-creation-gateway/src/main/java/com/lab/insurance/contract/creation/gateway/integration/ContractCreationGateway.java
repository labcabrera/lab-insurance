package com.lab.insurance.contract.creation.gateway.integration;

import org.lab.insurance.domain.action.contract.ContractCreation;
import org.lab.insurance.domain.action.contract.InitialPaymentReception;
import org.lab.insurance.domain.core.IntegrationConstants.Channels;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.insurance.Order;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface ContractCreationGateway {

	@Gateway(requestChannel = Channels.ContractCreationRequest,
			replyChannel = Channels.ContractCreationResponse,
			replyTimeout = 600000,
			requestTimeout = 600000)
	Contract processCreation(ContractCreation request);

	@Gateway(requestChannel = Channels.ContractApprobationRequest,
			replyChannel = Channels.ContractApprobationResponse,
			replyTimeout = 600000,
			requestTimeout = 600000)
	Contract processApprobation(Contract request);

	@Gateway(requestChannel = Channels.InitialPaymentReceptionRequest,
			replyChannel = Channels.InitialPaymentReceptionResponse,
			replyTimeout = 600000,
			requestTimeout = 600000)
	Order processPaymentReception(InitialPaymentReception request);

}
