package org.lab.insurance.engine.processors.contract;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.Constants;
import org.lab.insurance.model.HasContract;
import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.services.common.StateMachineService;

public class ContractStartProcessor implements Processor {

	@Inject
	private StateMachineService stateMachineService;

	@Override
	public void process(Exchange exchange) throws Exception {
		HasContract hasContract = exchange.getIn().getBody(HasContract.class);
		Contract contract = hasContract.getContract();
		stateMachineService.createTransition(contract, Constants.ContractStates.ACTIVE);
	}
}
