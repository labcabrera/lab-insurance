package org.lab.insurance.engine.processors.contract;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.common.services.StateMachineService;
import org.lab.insurance.domain.HasContract;
import org.lab.insurance.domain.contract.Contract;
import org.springframework.beans.factory.annotation.Autowired;

public class ContractStartProcessor implements Processor {

	@Autowired
	private StateMachineService stateMachineService;

	@Override
	public void process(Exchange exchange) throws Exception {
		HasContract hasContract = exchange.getIn().getBody(HasContract.class);
		Contract contract = hasContract.getContract();
		stateMachineService.createTransition(contract, Contract.States.STARTED);
	}
}
