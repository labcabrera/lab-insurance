package org.lab.insurance.engine.processors.prices;

import static org.apache.commons.lang3.time.DateFormatUtils.ISO_DATE_FORMAT;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.ValidationException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import org.lab.insurance.engine.model.prices.GuaranteePriceCreationAction;
import org.lab.insurance.model.jpa.insurance.AssetGuaranteePercent;
import org.lab.insurance.model.jpa.insurance.AssetPrice;
import org.lab.insurance.services.insurance.CotizationsService;

/**
 * Procesador que comprueba que:
 * <ul>
 * <li>No hay colisiones en la tabla {@link AssetGuaranteePercent}.</li>
 * <li>No hay colisiones en la tabla de precios.</li>
 * <li>Si existe un {@link AssetGuaranteePercent} el nuevo valor debe empezar por el siguiente dia a la fecha de finalizacion del registro.</li>
 * <li>Si existen precios el nuevo valor debe empezar por el siguiente dia al ultimo precio generado.</li>
 * </ul>
 */
public class GuaranteePriceCreationValidator implements Processor {

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	@Inject
	private CotizationsService cotizationsService;

	@Override
	public void process(Exchange exchange) throws Exception {
		GuaranteePriceCreationAction action = exchange.getIn().getBody(GuaranteePriceCreationAction.class);
		checkActionData(action);
		checkPrices(action);
		checkAssetGuaranteePercent(action);

	}

	private void checkActionData(GuaranteePriceCreationAction action) {
		if (action.getFrom() == null) {
			throw new ValidationException("Missing initial generation date");
		} else if (action.getTo() == null) {
			throw new ValidationException("Missing final generation date");
		} else if (action.getTo().before(action.getFrom())) {
			throw new ValidationException("Final date cant be before initial date");
		}
	}

	private void checkPrices(GuaranteePriceCreationAction action) {
		AssetPrice lastPrice = cotizationsService.findLastPrice(action.getAsset(), action.getTo());
		if (lastPrice != null) {
			// Si existen precios la fecha de generacion ha de ser la inmediatamente siguiente al ultimo precio generado
			Date checkDateFrom = new DateTime(lastPrice.getPriceDate()).plusDays(1).toDate();
			if (checkDateFrom.compareTo(action.getFrom()) != 0) {
				String template = "Initial date doesnt match with expected value %s. Value must be equal to the last price date plus one day";
				throw new ValidationException(String.format(template, ISO_DATE_FORMAT.format(checkDateFrom)));
			}
		}
		if (!cotizationsService.findPricesInRange(action.getAsset(), action.getFrom(), action.getTo()).isEmpty()) {
			String template = "Already exists prices for asset %s in range (%s, %s)";
			throw new ValidationException(String.format(template, action.getAsset().getIsin(), ISO_DATE_FORMAT.format(action.getFrom()), ISO_DATE_FORMAT.format(action.getTo())));
		}
	}

	private void checkAssetGuaranteePercent(GuaranteePriceCreationAction action) {
		EntityManager entityManager = entityManagerProvider.get();
		String qlString = "select e from AssetGuaranteePercent e where e.asset = :asset order by e.to desc";
		TypedQuery<AssetGuaranteePercent> query = entityManager.createQuery(qlString, AssetGuaranteePercent.class).setParameter("asset", action.getAsset()).setMaxResults(1);
		List<AssetGuaranteePercent> list = query.getResultList();
		if (!list.isEmpty()) {
			AssetGuaranteePercent last = list.iterator().next();
			Date checkDateFrom = new DateTime(last.getTo()).plusDays(1).toDate();
			if (checkDateFrom.compareTo(action.getFrom()) != 0) {
				String template = "Initial date doesnt match with expected value %s. Value must be equal to the last guarantee range plus one day";
				throw new ValidationException(String.format(template, ISO_DATE_FORMAT.format(checkDateFrom)));
			}
		}
	}
}
