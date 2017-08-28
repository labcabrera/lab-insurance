package org.lab.insurance.domain;

import org.lab.insurance.domain.contract.Contract;

public interface HasContract {

	Contract getContract();

	void setContract(Contract contract);

}
