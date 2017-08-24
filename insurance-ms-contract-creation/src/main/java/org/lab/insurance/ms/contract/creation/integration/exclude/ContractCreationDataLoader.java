package org.lab.insurance.ms.contract.creation.integration.exclude;

import org.apache.commons.lang.Validate;
import org.lab.insurance.model.insurance.BaseAsset;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.insurance.OrderDistribution;
import org.lab.insurance.model.insurance.repository.BaseAssetRepository;
import org.lab.insurance.ms.contract.creation.domain.ContractCreationData;
import org.lab.insurance.ms.contract.creation.integration.IntegrationConstants.Channels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Filter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ContractCreationDataLoader {

	@Autowired
	private BaseAssetRepository repository;

	@Filter(inputChannel = Channels.ContractCreationIn, outputChannel = Channels.ContractCreationProcess)
	public ContractCreationData buildRequest(Message<ContractCreationData> msg) {
		log.info("Loading data");
		ContractCreationData result = msg.getPayload();
		Order initialPayment = result.getInitialPayment();
		for (OrderDistribution i : initialPayment.getBuyDistribution()) {
			if (i.getAsset().getId() == null) {
				if (i.getAsset().getIsin() != null) {
					String isin = i.getAsset().getIsin();
					BaseAsset asset = repository.findByIsin(isin);
					Validate.notNull(asset, "Unknown asset " + i.getAsset());
					i.setAsset(asset);
				}
				else {
					throw new RuntimeException("Undefined asset");
				}
			}
		}
		return result;
	}
}
