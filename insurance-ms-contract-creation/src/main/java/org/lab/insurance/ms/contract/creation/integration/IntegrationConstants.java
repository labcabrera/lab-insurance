package org.lab.insurance.ms.contract.creation.integration;

public interface IntegrationConstants {

	public interface Channels {
		String ContractCreationIn = "channel/cotract-creation/in";
		String ContractCreationProcess = "channel/cotract-creation/process";
		String ContractCreationOut = "channel/cotract-creation/out";
		String ContractCreationError = "channel/cotract-creation/error";
	}

}
