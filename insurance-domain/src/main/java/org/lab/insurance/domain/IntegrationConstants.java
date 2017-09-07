package org.lab.insurance.domain;

public interface IntegrationConstants {

	public interface Queues {
		String ContractCreation = "contract-create.request";
		String PortfolioInitialization = "portfolio-create.request";
		String PortfolioInitializationResponse = "portfolio-create.response";
		String PortfolioInitializationError = "portfolio-create.error";
		String OrderCreationRequest = "order-create.request";
		String ContractInitialDocRequest = "contract-create-doc.request";
	}

	public interface Channels {
		String ContractRequest = "channel-contract-request";
		String ContractResponse = "channel-contract-response";
		String PortfolioInitializationRequest = "channel-portfolio-initialization-request";
		String PortfolioInitializationResponse = "channel-portfolio-initialization-response";
		String ContractCreatedSubscribeChannel = "channel-contract-created-subscribe";
	}

}
