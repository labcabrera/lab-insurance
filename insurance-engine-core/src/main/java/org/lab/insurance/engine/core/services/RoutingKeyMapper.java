package org.lab.insurance.engine.core.services;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.lab.insurance.common.exception.InsuranceException;
import org.lab.insurance.domain.action.contract.ContractApprobation;
import org.lab.insurance.domain.action.contract.ContractCreation;
import org.lab.insurance.domain.action.contract.InitialPaymentReception;
import org.lab.insurance.domain.action.contract.OrderValorization;
import org.lab.insurance.engine.core.domain.InsuranceTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

//TODO pasar a DSL, esto es un workaround hasta que se termine de perfilar el sistema de acciones
@Component
@SuppressWarnings("rawtypes")
public class RoutingKeyMapper {

	@Autowired
	private Environment env;

	private Map<Class, String> mapping;
	private Map<Class, Boolean> mappingSync;

	@PostConstruct
	public void initalize() {
		mapping = new HashMap<>();
		mapping.put(ContractCreation.class, env.getProperty("queues.contract.creation"));
		mapping.put(ContractApprobation.class, env.getProperty("queues.contract.approbation"));
		mapping.put(InitialPaymentReception.class, env.getProperty("queues.payment.initial-payment-reception"));
		mapping.put(OrderValorization.class, env.getProperty("queues.order.valorization"));
		mappingSync = new HashMap<>();
		mappingSync.put(ContractCreation.class, true);
		mappingSync.put(ContractApprobation.class, true);
		mappingSync.put(InitialPaymentReception.class, true);
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
