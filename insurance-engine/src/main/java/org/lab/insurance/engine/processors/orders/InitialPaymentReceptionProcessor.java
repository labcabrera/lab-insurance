package org.lab.insurance.engine.processors.orders;

import java.util.List;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.Constants;
import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.contract.ContractDates;
import org.lab.insurance.model.contract.repository.ContractRepository;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.services.common.StateMachineService;
import org.lab.insurance.services.common.TimestampProvider;
import org.lab.insurance.services.insurance.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Procesador que se ejecuta cuando se recibe la confirmacion de un pago inicial.
 *
 */
public class InitialPaymentReceptionProcessor implements Processor {

	@Autowired
	private ContractRepository contractRepository;
	@Inject
	private StateMachineService stateMachineService;
	@Inject
	private TimestampProvider timestampProvider;

	@Inject
	private OrderService orderService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		Contract contract = order.getContract();
		boolean checkStarted = checkContractStarted(contract);
		if (checkStarted) {
			ContractDates contractDates = contract.getDates();
			if (contractDates == null) {
				contract.setDates(new ContractDates());
			}
			contract.getDates().setStartDate(timestampProvider.getCurrentDate());
			stateMachineService.createTransition(contract, Constants.ContractStates.PAYED);
			contractRepository.save(contract);
		}
	}

	public boolean checkContractStarted(Contract policy) {
		List<Order> orders = orderService.selectByContractNotInStates(policy, Constants.OrderStates.INITIAL);
		return orders.size() != 0;
	}

}
