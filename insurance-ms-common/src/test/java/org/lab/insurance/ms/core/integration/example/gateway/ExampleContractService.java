package org.lab.insurance.ms.core.integration.example.gateway;

import org.lab.insurance.domain.contract.Contract;
import org.lab.insurance.ms.core.integration.example.DemoConstants;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(name = DemoConstants.ENTRY_GATEWAY, defaultRequestChannel = DemoConstants.CHANNEL_REQUEST)
public interface ExampleContractService {

	public Contract store(Contract contract);
}
