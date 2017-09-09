package org.lab.insurance.portfolio.gateway.integration;

import org.lab.insurance.domain.core.IntegrationConstants.Channels;
import org.lab.insurance.domain.core.messaging.ContractRefMessage;
import org.lab.insurance.domain.core.portfolio.ContractPortfolioRelation;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface ContractCreationGateway {

	@Gateway(requestChannel = Channels.PortfolioInitializationRequest,
			replyChannel = Channels.PortfolioInitializationResponse)
	ContractPortfolioRelation process(ContractRefMessage msg);

}
