package org.lab.insurance.model;

import org.lab.insurance.model.contract.Contract;

public interface HasContract {

	Contract getContract();

	void setContract(Contract contract);

}
