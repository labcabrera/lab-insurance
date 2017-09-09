package org.lab.insurance.engine.processors.orders;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.engine.ActionExecutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleFeesOrder implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(ScheduleFeesOrder.class);

	// @Inject
	// private TriggerActivationService triggerActivationService;
	@Inject
	private ActionExecutionService actionExecutionService;

	@Override
	public void process(Exchange exchange) throws Exception {
		throw new RuntimeException("Not implemented");
		// ContractStartAction action = exchange.getIn().getBody(ContractStartAction.class);
		// Contract contract = action.getContract();
		// TriggerDefinition triggerDefinition = contract.getAgreement().getServiceInfo().getFeesTriggerDefinition();
		// if (triggerDefinition == null) {
		// LOG.warn("Missing fees trigger definition in agreement {}", contract.getAgreement());
		// return;
		// }
		// Date when = contract.getDates().getStartDate();
		// if (when == null) {
		// LOG.warn("Cant resolve fees activation date");
		// return;
		// }
		// Date executionDate = triggerActivationService.getNextActivation(triggerDefinition, when);
		// ExecuteFeesAction executeFeesAction = new ExecuteFeesAction().withContractId(contract.getId());
		// executeFeesAction.setActionDate(executionDate);
		// actionExecutionService.schedule(executeFeesAction, when);
	}
}
