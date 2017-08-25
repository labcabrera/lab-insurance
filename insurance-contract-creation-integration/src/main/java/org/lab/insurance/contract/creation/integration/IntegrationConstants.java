package org.lab.insurance.contract.creation.integration;

public interface IntegrationConstants {

	public interface Queues {
		String ContractRequest = "queue-contract-request";
	}

	public interface Channels {
		String ContractRequest = "channel-contract-request";
		String ContractResponse = "channel-contract-response";
	}

}
