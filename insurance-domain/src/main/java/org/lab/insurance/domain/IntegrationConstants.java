package org.lab.insurance.domain;

public interface IntegrationConstants {

	public interface Queues {
		String ContractRequest = "q/contract/create/request";
		String PortfolioInitializationRequest = "q/portfolio/init/request";
		String PortfolioInitializationResponse = "q/portfolio/init/response";
		String OrderCreationRequest = "q/order/create/request";
	}

	public interface Channels {
		String ContractRequest = "channel-contract-request";
		String ContractResponse = "channel-contract-response";
		String PortfolioInitializationRequest = "channel-portfolio-initialization-request";
		String PortfolioInitializationResponse = "channel-portfolio-initialization-response";
		String ContractCreatedSubscribeChannel = "channel-contract-created-subscribe";
	}

}
