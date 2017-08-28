package org.lab.insurance.portfolio.gateway.integration;

import org.lab.insurance.domain.IntegrationConstants.Channels;
import org.lab.insurance.domain.messaging.ContractRefMessage;
import org.lab.insurance.portfolio.core.domain.ContractPortfolioRelation;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface ContractCreationGateway {

	@Gateway(requestChannel = Channels.PortfolioInitializationRequest,
			replyChannel = Channels.PortfolioInitializationResponse)
	ContractPortfolioRelation process(ContractRefMessage msg);

}
