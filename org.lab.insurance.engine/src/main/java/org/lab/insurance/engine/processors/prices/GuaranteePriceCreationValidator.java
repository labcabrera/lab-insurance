package org.lab.insurance.engine.processors.prices;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.engine.actions.prices.GuaranteePriceCreationAction;
import org.lab.insurance.model.jpa.insurance.AssetGuaranteePercent;

/**
 * Procesador que comprueba que:
 * <ul>
 * <li>No hay colisiones en la tabla {@link AssetGuaranteePercent}.</li>
 * <li>No hay colisiones en la tabla de precios.</li>
 * <li>Si existe un {@link AssetGuaranteePercent} el nuevo valor debe empezar por el siguiente dia a la fecha de
 * finalizacion del registro.</li>
 * <li>Si existen precios el nuevo valor debe empezar por el siguiente dia al ultimo precio generado.</li>
 * </ul>
 */
public class GuaranteePriceCreationValidator implements Processor {

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	@Override
	public void process(Exchange exchange) throws Exception {
		GuaranteePriceCreationAction action = exchange.getIn().getBody(GuaranteePriceCreationAction.class);
	}

	private void test() {
		EntityManager entityManager = entityManagerProvider.get();
		TypedQuery<AssetGuaranteePercent> queryPercent = entityManager.createQuery("", AssetGuaranteePercent.class);

	}

}
