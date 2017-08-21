package org.lab.insurance.engine.processors.prices;

import static org.apache.commons.lang3.time.DateFormatUtils.ISO_DATE_FORMAT;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.time.DateUtils;
import org.lab.insurance.core.math.BigMath;
import org.lab.insurance.engine.model.prices.GuaranteePriceCreationAction;
import org.lab.insurance.model.insurance.AssetPrice;
import org.lab.insurance.model.insurance.BaseAsset;
import org.lab.insurance.model.insurance.Currency;
import org.lab.insurance.services.insurance.CotizationsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Procesador encargado de generar los precios de un tipo garantizado.
 * 
 * @see GuaranteePriceCreationValidator
 */
public class GuaranteePriceCreationProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(GuaranteePriceCreationProcessor.class);

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	@Inject
	private CotizationsService cotizationsService;

	@Override
	public void process(Exchange exchange) throws Exception {
		EntityManager entityManager = entityManagerProvider.get();
		Currency currency = resolveGuaranteeCurrency();
		GuaranteePriceCreationAction action = exchange.getIn().getBody(GuaranteePriceCreationAction.class);
		BaseAsset asset = action.getAsset();
		Date from = action.getFrom();
		Date to = action.getTo();
		BigDecimal guarantee = action.getPercent();
		LOG.info("Creating guarantee prices for {} at {}% from {} to {}", asset.getName(), guarantee, from != null ? ISO_DATE_FORMAT.format(from) : "", ISO_DATE_FORMAT.format(to));
		AssetPrice lastPrice = cotizationsService.findLastPrice(asset, to);
		BigDecimal initialPrice = lastPrice == null ? BigDecimal.ONE : lastPrice.getPrice();
		Date now = Calendar.getInstance().getTime();
		GregorianCalendar check = new GregorianCalendar();
		check.setTime(from);
		BigDecimal totalDays = check.isLeapYear(check.get(Calendar.YEAR)) ? new BigDecimal(366) : new BigDecimal(365);
		BigDecimal elemento = BigDecimal.ONE.add(guarantee.divide(BigMath.HUNDRED));
		BigDecimal count = BigDecimal.ONE;
		BigDecimal partial;
		while (from.compareTo(to) <= 0) {
			check.setTime(from);
			BigDecimal potencia = count.divide(totalDays, 15, RoundingMode.HALF_EVEN);
			double newValue = StrictMath.pow(elemento.doubleValue(), potencia.doubleValue());
			partial = initialPrice.multiply(new BigDecimal(newValue)).setScale(15, RoundingMode.HALF_EVEN);
			AssetPrice price = new AssetPrice();
			price.setAsset(asset);
			price.setGenerated(now);
			price.setBuyPrice(partial);
			price.setSellPrice(partial);
			price.setPrice(partial);
			price.setPriceDate(from);
			price.setCurrency(currency);
			entityManager.persist(price);
			from = DateUtils.addDays(from, 1);
			count = count.add(BigDecimal.ONE);
		}
	}

	/**
	 * NOTA: los precios del el garantizado carecen de divisa (no deja de ser una serie de numeros calculados segun la formula de
	 * revalorizacion) ya que no aunque el modelo obliga a establecerla de modo que la fijamos a EURO.
	 * 
	 * @return
	 */
	private Currency resolveGuaranteeCurrency() {
		return entityManagerProvider.get().find(Currency.class, "EUR");
	}
}
