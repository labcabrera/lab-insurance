package org.lab.insurance.engine.processors.contract;

import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.Constants;
import org.lab.insurance.model.HasContract;
import org.lab.insurance.model.jpa.contract.Contract;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.services.common.StateMachineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Procesador encargado de persistir la informacion de una poliza y procesar el pago inicial.
 */
public class NewContractProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(NewContractProcessor.class);

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	@Inject
	private Validator validator;
	@Inject
	private StateMachineService stateMachineService;

	@Override
	public void process(Exchange exchange) throws Exception {
		LOG.info("Processing new policy");
		Contract contract = exchange.getIn().getBody(HasContract.class).getContract();
		Set<ConstraintViolation<Contract>> validationResults = validator.validate(contract);
		if (!validationResults.isEmpty()) {
			throw new RuntimeException("Validation errors");
		}
		EntityManager entityManager = entityManagerProvider.get();
		// Actualizamos la referencia de las ordenes
		for (Order order : contract.getOrders()) {
			order.setContract(contract);
		}
		entityManager.persist(contract);
		entityManager.flush();
		stateMachineService.createTransition(contract, Constants.ContractStates.INITIAL);
	}
}
