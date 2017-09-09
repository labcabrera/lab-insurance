package com.lab.insurance.contract.creation.gateway.integration;

import org.lab.insurance.contract.creation.core.domain.PaymentReceptionData;
import org.lab.insurance.domain.action.ContractCreation;
import org.lab.insurance.domain.core.IntegrationConstants.Channels;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.insurance.Order;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface ContractCreationGateway {

	@Gateway(requestChannel = Channels.ContractCreationRequest, replyChannel = Channels.ContractCreationResponse)
	Contract processCreation(ContractCreation request);

	@Gateway(requestChannel = Channels.ContractApprobationRequest, replyChannel = Channels.ContractApprobationResponse)
	Contract processApprobation(Contract request);

	@Gateway(requestChannel = Channels.InitialPaymentReceptionRequest,
			replyChannel = Channels.InitialPaymentReceptionResponse)
	Order processPaymentReception(PaymentReceptionData request);

}
