package org.lab.insurance.engine.processors.financial;

import org.lab.insurance.engine.model.ActionDefinition;
import org.lab.insurance.engine.model.contract.ContractBaseAction;

@ActionDefinition(endpoint = "direct:execute_fees")
@SuppressWarnings("serial")
public class ExecuteFeesAction extends ContractBaseAction {

}
