package org.lab.insurance.engine.processors.policy;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.Constants;
import org.lab.insurance.model.jpa.contract.Contract;
import org.lab.insurance.services.common.StateMachineService;

public class ContractStartProcessor implements Processor {

	@Inject
	private StateMachineService stateMachineService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Contract contract = exchange.getIn().getBody(Contract.class);
		stateMachineService.createTransition(contract, Constants.ContractStates.ACTIVE);
	}
}
