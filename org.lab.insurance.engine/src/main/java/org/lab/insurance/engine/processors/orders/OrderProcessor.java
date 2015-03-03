package org.lab.insurance.engine.processors.orders;

import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.Constants;
import org.lab.insurance.model.jpa.Policy;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.model.jpa.insurance.OrderProcessInfo;
import org.lab.insurance.services.common.StateMachineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrderProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(OrderProcessor.class);

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	@Inject
	private StateMachineService stateMachineService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		LOG.debug("Processing order {}", order);
		resolveOrderPortfolios(order);
		order.getDates().setProcessed(Calendar.getInstance().getTime());
		EntityManager entityManager = entityManagerProvider.get();
		entityManager.merge(order);
		entityManager.flush();
		stateMachineService.createTransition(order, Constants.OrderStates.PROCESSED);
	}

	private void resolveOrderPortfolios(Order order) {
		if (order.getProcessInfo() == null) {
			Policy policy = order.getPolicy();
			order.setProcessInfo(new OrderProcessInfo());
			order.getProcessInfo().setPortfolioActivo(policy.getPortfolioInfo().getPortfolioActivo());
			order.getProcessInfo().setPortfolioPasivo(policy.getPortfolioInfo().getPortfolioPasivo());
		}
	}
}
