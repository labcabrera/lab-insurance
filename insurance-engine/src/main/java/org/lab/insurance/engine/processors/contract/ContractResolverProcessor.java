package org.lab.insurance.engine.processors.contract;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.lab.insurance.model.HasContract;
import org.lab.insurance.model.contract.Contract;

public class ContractResolverProcessor implements Processor {

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	@Override
	public void process(Exchange exchange) throws Exception {
		if (exchange.getIn().getBody(HasContract.class) != null) {
			HasContract hasContract = exchange.getIn().getBody(HasContract.class);
			if (hasContract.getContract() != null && StringUtils.isNotBlank(hasContract.getContract().getId())) {
				Contract contract = entityManagerProvider.get().find(Contract.class, hasContract.getContract().getId());
				hasContract.setContract(contract);
			}
		}

	}

}
