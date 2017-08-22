package org.lab.insurance.engine.model.contract;

import java.util.Date;

import org.lab.insurance.engine.model.ActionEntity;
import org.lab.insurance.model.HasContract;
import org.lab.insurance.model.contract.Contract;

@SuppressWarnings("serial")
public abstract class ContractBaseAction implements ActionEntity<Contract>, HasContract {

	protected Contract contract;
	protected Date actionDate;

	@Override
	public Contract getContract() {
		return contract;
	}

	@Override
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

	// @SuppressWarnings("unchecked")
	// public <T> T withContractId(String contractId) {
	// contract = new Contract();
	// contract.setId(contractId);
	// return (T) this;
	// }

	@SuppressWarnings("unchecked")
	public <T> T withActionDate(Date actionDate) {
		this.actionDate = actionDate;
		return (T) this;
	}
}
