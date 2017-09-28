package org.lab.insurance.domain.core;

public interface IntegrationConstants {

	public interface Channels {
		String ContractCreationRequest = "contract-create-channel.request";
		String ContractCreationResponse = "contract-create-channel.reponse";
		String ContractApprobationRequest = "contract-approbation-channel.request";
		String ContractApprobationResponse = "contract-approbation-channel.response";
		String PortfolioInitializationRequest = "portfolio-initialization-channel.request";
		String PortfolioInitializationResponse = "portfolio-iinitialization-channel.response";
		String InitialPaymentReceptionRequest = "initial-payment-reception-channel.request";
		String InitialPaymentReceptionResponse = "initial-payment-reception-channel.response";
	}

}
