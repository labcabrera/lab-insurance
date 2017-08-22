package org.lab.insurance.engine.processors.contract;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.HasContract;
import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.contract.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ContractResolverProcessor implements Processor {

	@Autowired
	private ContractRepository contractRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
		if (exchange.getIn().getBody(HasContract.class) != null) {
			HasContract hasContract = exchange.getIn().getBody(HasContract.class);
			if (hasContract.getContract() != null && hasContract.getContract().getId() != null) {
				Contract contract = contractRepository.findOne(hasContract.getContract().getId());
				hasContract.setContract(contract);
			}
		}

	}

}
