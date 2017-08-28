package org.lab.insurance.domain;

public interface IntegrationConstants {

	public interface Queues {
		String ContractRequest = "q/contract/create/request";
		String PortfolioInitializationRequest = "q/portfolio/init/request";
	}

	public interface Channels {
		String ContractRequest = "channel-contract-request";
		String ContractResponse = "channel-contract-response";
	}

}
