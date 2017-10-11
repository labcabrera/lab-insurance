package org.lab.insurance.contract.creation.core.service;

import org.apache.commons.lang3.StringUtils;
import org.lab.insurance.common.exception.InsuranceException;
import org.lab.insurance.domain.action.contract.InitialPaymentReception;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.contract.repository.ContractRepository;
import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.domain.core.insurance.OrderDates;
import org.lab.insurance.domain.core.insurance.OrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InitialPaymentReceptionProcessor {

	@Autowired
	private ContractRepository contractRepo;

	public Contract process(InitialPaymentReception request) {
		log.info("Processing initial payment reception {}", request);
		Assert.notNull(request, "Null request");
		Assert.notNull(request.getContractId(), "Missing contract identifier");
		Assert.isTrue(StringUtils.isNotBlank(request.getContractId()), "Missing contract identifier");
		String contractId = request.getContractId();

		Contract contract = contractRepo.findById(contractId)
				.orElseThrow(() -> new InsuranceException("Unknow contract " + contractId));
		Order payment = contract.filterOrders(OrderType.INITIAL_PAYMENT).iterator().next();
		payment.setContract(contract);
		if (payment.getDates() == null) {
			payment.setDates(new OrderDates());
			payment.getDates().setEffective(request.getPaymentReception());
		}
		return contract;
	}

}
