package org.lab.insurance.engine.processors.prices;

import static org.apache.commons.lang3.time.DateFormatUtils.ISO_DATE_FORMAT;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.validation.ValidationException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.joda.time.DateTime;
import org.lab.insurance.model.engine.actions.prices.GuaranteePriceCreationAction;
import org.lab.insurance.model.jpa.insurance.AssetGuaranteePercent;
import org.lab.insurance.model.jpa.insurance.AssetPrice;
import org.lab.insurance.model.jpa.insurance.BaseAsset;
import org.lab.insurance.services.insurance.CotizationsService;

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
	@Inject
	private CotizationsService cotizationsService;

	@Override
	public void process(Exchange exchange) throws Exception {
		GuaranteePriceCreationAction action = exchange.getIn().getBody(GuaranteePriceCreationAction.class);
		BaseAsset asset = action.getAsset();
		Date from = action.getFrom();
		Date to = action.getTo();
		AssetPrice lastPrice = cotizationsService.findLastPrice(asset, to);
		if (lastPrice == null && from == null) {
			throw new ValidationException("Missing initial generation date");
		} else if (from != null && from.after(to)) {
			throw new ValidationException("Initial date is after final date");
		} else if (!cotizationsService.findPricesInRange(asset, from, to).isEmpty()) {
			throw new ValidationException("Ya existen precios de " + asset.getName() + " en el rango [" + ISO_DATE_FORMAT.format(from) + ", " + ISO_DATE_FORMAT.format(to) + "]");
		}
		if (lastPrice != null) {
			Date lastDayPriceTruncated = new DateTime(lastPrice.getPriceDate()).plusDays(1).withTimeAtStartOfDay().toDate();
			Date fromTruncated = new DateTime(from).withTimeAtStartOfDay().toDate();
			if (from != null && lastDayPriceTruncated.compareTo(fromTruncated) != 0) {
				String messageTemplate = "Error al cargar los precios del garantizado %s. Se ha indicado como fecha de inicio %s cuando el ultimo precio es de %s";
				throw new ValidationException(String.format(messageTemplate, asset.getName(), ISO_DATE_FORMAT.format(from), ISO_DATE_FORMAT.format(lastPrice)));
			}
		}
	}

}
