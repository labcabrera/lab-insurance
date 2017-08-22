package org.lab.insurance.ms.contract.creation.integration.gateway;

import org.lab.insurance.model.contract.Contract;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(name = "contractCreationGateway", defaultRequestChannel = "requestChannel")
public interface ContractCreationGateway {

	public Contract process(Contract contract);

}
