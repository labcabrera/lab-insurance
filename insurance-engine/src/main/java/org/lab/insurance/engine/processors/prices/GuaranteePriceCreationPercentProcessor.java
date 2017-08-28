package org.lab.insurance.engine.processors.prices;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.domain.insurance.AssetGuaranteePercent;
import org.lab.insurance.domain.insurance.repository.AssetGuaranteePercentRepository;
import org.lab.insurance.engine.model.prices.GuaranteePriceCreationAction;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Procesador encargado de generar una entidad de tipo {@link GuaranteePriceCreationAction} cada vez que se genera un
 * precio a traves de la accion {@link GuaranteePriceCreationAction}.
 * 
 * @see GuaranteePriceCreationValidator
 */
public class GuaranteePriceCreationPercentProcessor implements Processor {

	@Autowired
	private AssetGuaranteePercentRepository assetGuaranteePercentRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
		GuaranteePriceCreationAction action = exchange.getIn().getBody(GuaranteePriceCreationAction.class);
		AssetGuaranteePercent entity = new AssetGuaranteePercent();
		entity.setAsset(action.getAsset());
		entity.setFrom(action.getFrom());
		entity.setTo(action.getTo());
		entity.setGuaranteePercent(action.getPercent());
		assetGuaranteePercentRepository.save(entity);
	}
}
