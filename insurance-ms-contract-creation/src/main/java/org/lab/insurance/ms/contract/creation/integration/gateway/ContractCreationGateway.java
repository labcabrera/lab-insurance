package org.lab.insurance.ms.contract.creation.integration.gateway;

import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.ms.contract.creation.domain.ContractCreationData;
import org.lab.insurance.ms.contract.creation.integration.IntegrationConstants.Channels;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(name = "contractCreationGateway", defaultRequestChannel = Channels.ContractCreationIn)
public interface ContractCreationGateway {

	public Contract process(ContractCreationData data);

}
