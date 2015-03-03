package org.lab.insurance.engine.processors.prices;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.engine.model.prices.GuaranteePriceCreationAction;
import org.lab.insurance.model.jpa.insurance.AssetGuaranteePercent;

/**
 * Procesador encargado de generar una entidad de tipo {@link GuaranteePriceCreationAction} cada vez que se genera un precio a traves de la
 * accion {@link GuaranteePriceCreationAction}.
 * 
 * @see GuaranteePriceCreationValidator
 */
public class GuaranteePriceCreationPercentProcessor implements Processor {

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	@Override
	public void process(Exchange exchange) throws Exception {
		GuaranteePriceCreationAction action = exchange.getIn().getBody(GuaranteePriceCreationAction.class);
		AssetGuaranteePercent entity = new AssetGuaranteePercent();
		entity.setAsset(action.getAsset());
		entity.setFrom(action.getFrom());
		entity.setTo(action.getTo());
		entity.setGuaranteePercent(action.getPercent());
		EntityManager entityManager = entityManagerProvider.get();
		entityManager.persist(entity);
		entityManager.flush();
	}
}
