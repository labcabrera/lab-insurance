package org.lab.insurance.engine.model.contract;

import java.util.Date;

import org.lab.insurance.engine.model.ActionDefinition;
import org.lab.insurance.engine.model.ActionEntity;
import org.lab.insurance.model.HasContract;
import org.lab.insurance.model.jpa.contract.Contract;

@ActionDefinition(endpoint = "direct:new_contract_action")
@SuppressWarnings("serial")
public class NewContractAction implements ActionEntity<Contract>, HasContract {

	private Contract contract;
	private Date actionDate;

	@Override
	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	@Override
	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
}
