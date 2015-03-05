package org.lab.insurance.engine.processors.orders;

import java.util.Date;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.engine.ActionExecutionService;
import org.lab.insurance.engine.model.contract.ContractStartAction;
import org.lab.insurance.engine.processors.financial.ExecuteFeesAction;
import org.lab.insurance.model.jpa.contract.Contract;
import org.lab.insurance.model.jpa.contract.TriggerDefinition;
import org.lab.insurance.services.common.TriggerActivationService;

public class ScheduleFeesOrder implements Processor {

	@Inject
	private TriggerActivationService triggerActivationService;
	@Inject
	private ActionExecutionService actionExecutionService;

	@Override
	public void process(Exchange exchange) throws Exception {
		ContractStartAction action = exchange.getIn().getBody(ContractStartAction.class);
		Contract contract = action.getContract();
		TriggerDefinition triggerDefinition = contract.getAgreement().getServiceInfo().getFeesTriggerDefinition();
		Date when = contract.getStartDate();
		Date executionDate = triggerActivationService.getNextActivation(triggerDefinition, when);
		ExecuteFeesAction executeFeesAction = new ExecuteFeesAction().withContractId(contract.getId());
		executeFeesAction.setActionDate(executionDate);
		actionExecutionService.schedule(executeFeesAction, when);
	}
}
