package org.lab.insurance.contract.creation.core.service;

import org.apache.commons.lang3.StringUtils;
import org.lab.insurance.common.services.StateMachineService;
import org.lab.insurance.contract.creation.core.domain.PaymentReceptionData;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.contract.repository.ContractRepository;
import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.domain.core.insurance.OrderDates;
import org.lab.insurance.domain.core.insurance.OrderType;
import org.lab.insurance.domain.core.insurance.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InitialPaymentReceptionProcessor {

	@Autowired
	private ContractRepository contractRepo;
	@Autowired
	private OrderRepository orderRepo;
	@Autowired
	private StateMachineService stateMachineService;

	public Order process(PaymentReceptionData request) {
		Assert.notNull(request, "Null request");
		Assert.notNull(request.getContractId(), "Missing contract identifier");
		Assert.isTrue(StringUtils.isNotBlank(request.getContractId()), "Missing contract identifier");
		log.info("Processing initial payment reception {}", request);
		Contract contract = contractRepo.findOne(request.getContractId());
		Order payment = contract.filterOrders(OrderType.INITIAL_PAYMENT).iterator().next();
		payment.setContract(contract);
		if (payment.getDates() == null) {
			payment.setDates(new OrderDates());
			payment.getDates().setEffective(request.getPaymentReception());
		}
		stateMachineService.createTransition(payment, Order.States.INITIAL.name(), false);
		orderRepo.save(payment);
		return payment;
	}

}
