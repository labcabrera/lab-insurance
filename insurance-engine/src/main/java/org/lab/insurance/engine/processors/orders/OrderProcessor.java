package org.lab.insurance.engine.processors.orders;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.Constants;
import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.insurance.OrderProcessInfo;
import org.lab.insurance.model.insurance.repository.OrderRepository;
import org.lab.insurance.services.common.StateMachineService;
import org.lab.insurance.services.common.TimestampProvider;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrderProcessor implements Processor {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private StateMachineService stateMachineService;
	@Autowired
	private TimestampProvider timestampProvider;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		log.debug("Processing order {}", order);
		resolveOrderPortfolios(order);
		order.getDates().setProcessed(timestampProvider.getCurrentDateTime());
		orderRepository.save(order);
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
