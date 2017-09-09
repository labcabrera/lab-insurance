package org.lab.insurance.domain.core;

import org.lab.insurance.domain.core.contract.Contract;

public interface HasContract {

	Contract getContract();

	void setContract(Contract contract);

}
