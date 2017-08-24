package org.lab.insurance.ms.core.integration.example.endpoint;

import java.util.List;

import org.apache.commons.lang.Validate;
import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.insurance.BaseAsset;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.insurance.OrderDistribution;
import org.lab.insurance.model.insurance.OrderType;
import org.lab.insurance.model.insurance.repository.BaseAssetRepository;
import org.lab.insurance.ms.core.integration.example.DemoConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ContractInputTransformer {

	@Autowired
	private BaseAssetRepository repository;

	@Transformer(inputChannel = DemoConstants.CHANNEL_REQUEST, outputChannel = DemoConstants.CHANNEL_PROCESS)
	public Contract buildRequest(Message<Contract> msg) {
		log.info("Loading contract info (request >> invocation)");

		Contract contract = msg.getPayload();
		List<Order> initialPayments = contract.filterOrders(OrderType.INITIAL_PAYMENT);

		Validate.isTrue(initialPayments.size() == 1);
		Order initialPayment = initialPayments.iterator().next();

		for (OrderDistribution i : initialPayment.getBuyDistribution()) {
			if (i.getAsset().getId() == null) {
				if (i.getAsset().getIsin() != null) {
					String isin = i.getAsset().getIsin();
					BaseAsset asset = repository.findByIsin(isin);
					i.setAsset(asset);
				}
			}
		}
		return contract;
	}
}
