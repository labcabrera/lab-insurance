package org.lab.insurance.engine.core.services;

import java.util.HashMap;
import java.util.Map;

import org.lab.insurance.common.exception.InsuranceException;
import org.lab.insurance.domain.action.contract.ContractApprobation;
import org.lab.insurance.domain.action.contract.ContractCreation;
import org.lab.insurance.domain.action.contract.InitialPaymentReception;
import org.lab.insurance.domain.core.IntegrationConstants;
import org.lab.insurance.engine.core.domain.InsuranceTask;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("rawtypes")
public class RoutingKeyMapper {

	private Map<Class, String> mapping;
	private Map<Class, Boolean> mappingSync;

	public RoutingKeyMapper() {
		mapping = new HashMap<>();
		mapping.put(ContractCreation.class, IntegrationConstants.Queues.ContractCreation);
		mapping.put(ContractApprobation.class, IntegrationConstants.Queues.ContractApprobation);
		mapping.put(InitialPaymentReception.class, IntegrationConstants.Queues.InitialPaymentReception);
		mappingSync = new HashMap<>();
		mappingSync.put(ContractCreation.class, false);
		mappingSync.put(ContractApprobation.class, false);
		mappingSync.put(InitialPaymentReception.class, false);
	}

	public String getRoutingKey(InsuranceTask task) {
		Class actionClass = task.getAction().getClass();
		if (!mapping.containsKey(actionClass)) {
			throw new InsuranceException("Unmapped class " + actionClass.getName());
		}
		return mapping.get(actionClass);
	}

	public Boolean isSync(InsuranceTask task) {
		Class actionClass = task.getAction().getClass();
		if (!mapping.containsKey(actionClass)) {
			throw new InsuranceException("Unmapped class " + actionClass.getName());
		}
		return mappingSync.get(actionClass);
	}

}
