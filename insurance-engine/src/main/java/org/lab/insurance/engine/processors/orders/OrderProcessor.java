package org.lab.insurance.engine.processors.orders;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.Constants;
import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.insurance.OrderProcessInfo;
import org.lab.insurance.services.common.StateMachineService;
import org.lab.insurance.services.common.TimestampProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(OrderProcessor.class);

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	@Inject
	private StateMachineService stateMachineService;
	@Inject
	private TimestampProvider timestampProvider;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		LOG.debug("Processing order {}", order);
		resolveOrderPortfolios(order);
		order.getDates().setProcessed(timestampProvider.getCurrentDateTime());
		EntityManager entityManager = entityManagerProvider.get();
		entityManager.merge(order);
		entityManager.flush();
		stateMachineService.createTransition(order, Constants.OrderStates.PROCESSED);
	}

	private void resolveOrderPortfolios(Order order) {
		if (order.getProcessInfo() == null) {
			Contract policy = order.getContract();
			order.setProcessInfo(new OrderProcessInfo());
			order.getProcessInfo().setPortfolioActive(policy.getPortfolioInfo().getPortfolioActive());
			order.getProcessInfo().setPortfolioPassive(policy.getPortfolioInfo().getPortfolioPassive());
		}
	}
}
