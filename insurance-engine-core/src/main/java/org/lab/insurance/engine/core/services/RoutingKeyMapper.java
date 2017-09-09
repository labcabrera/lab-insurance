package org.lab.insurance.engine.core.services;

import java.util.HashMap;
import java.util.Map;

import org.lab.insurance.common.exception.InsuranceException;
import org.lab.insurance.domain.action.ContractCreation;
import org.lab.insurance.domain.core.IntegrationConstants;
import org.lab.insurance.engine.core.domain.InsuranceTask;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("rawtypes")
public class RoutingKeyMapper {

	private Map<Class, String> mapping;

	public RoutingKeyMapper() {
		mapping = new HashMap<>();
		mapping.put(ContractCreation.class, IntegrationConstants.Queues.ContractCreation);
	}

	public String getRoutingKey(InsuranceTask task) {
		Class actionClass = task.getAction().getClass();
		if (!mapping.containsKey(actionClass)) {
			throw new InsuranceException("Unmapped class " + actionClass.getName());
		}
		return mapping.get(actionClass);
	}

}
