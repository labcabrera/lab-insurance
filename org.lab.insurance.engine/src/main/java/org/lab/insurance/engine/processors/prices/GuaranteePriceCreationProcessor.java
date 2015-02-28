package org.lab.insurance.engine.processors.prices;

import static org.apache.commons.lang3.time.DateFormatUtils.ISO_DATE_FORMAT;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.validation.ValidationException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.lab.insurance.core.math.BigMath;
import org.lab.insurance.model.engine.actions.prices.GuaranteePriceCreationAction;
import org.lab.insurance.model.jpa.insurance.AssetPrice;
import org.lab.insurance.model.jpa.insurance.BaseAsset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuaranteePriceCreationProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(GuaranteePriceCreationProcessor.class);

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	@Override
	public void process(Exchange exchange) throws Exception {
		GuaranteePriceCreationAction action = exchange.getIn().getBody(GuaranteePriceCreationAction.class);
		createPrices(action.getAsset(), action.getFrom(), action.getTo(), action.getPercent());
	}

	public void createPrices(BaseAsset asset, Date from, Date to, BigDecimal guarantee) {
		LOG.info("Generando precios de {} al {}% [{},{}]", asset.getName(), guarantee, from != null ? ISO_DATE_FORMAT.format(from) : "", ISO_DATE_FORMAT.format(to));
		AssetPrice lastPrice = findLastPrice(asset, to);
		if (lastPrice == null && from == null) {
			throw new ValidationException("La fecha de inicio es necesaria en la primera generacion de precios");
		} else if (from != null && from.after(to)) {
			throw new ValidationException("Fecha hasta es anterior a fecha desde!");
		} else if (findPricesInRange(asset, from, to)) {
			throw new ValidationException("Ya existen precios de " + asset.getName() + " en el rango [" + ISO_DATE_FORMAT.format(from) + ", " + ISO_DATE_FORMAT.format(to) + "]");
		}
		BigDecimal initialPrice;
		if (lastPrice == null) {
			initialPrice = BigDecimal.ONE;
		} else {
			Date lastDayPriceTruncated = new DateTime(lastPrice.getPriceDate()).plusDays(1).withTimeAtStartOfDay().toDate();
			Date fromTruncated = new DateTime(from).withTimeAtStartOfDay().toDate();
			if (from != null && lastDayPriceTruncated.compareTo(fromTruncated) != 0) {
				String messageTemplate = "Error al cargar los precios del garantizado %s. Se ha indicado como fecha de inicio %s cuando el ultimo precio es de %s";
				throw new ValidationException(String.format(messageTemplate, asset.getName(), ISO_DATE_FORMAT.format(from), ISO_DATE_FORMAT.format(lastPrice)));
			}
			initialPrice = lastPrice.getPriceInEuros();
			from = new DateTime(lastPrice.getPriceDate()).plusDays(1).toDate();
		}
		// AssetPrice price = new AssetPrice(asset.getId(), from, current, null);
		// price.setCreated(timestampProvider.getCurrentTime());
		// entityManagerProvider.get().persist(price);
		Date now = Calendar.getInstance().getTime();
		GregorianCalendar check = new GregorianCalendar();
		check.setTime(from);
		BigDecimal totalDays = check.isLeapYear(check.get(Calendar.YEAR)) ? new BigDecimal(366) : new BigDecimal(365);
		BigDecimal elemento = BigDecimal.ONE.add(guarantee.divide(BigMath.CIEN));
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
			price.setBuyPriceInEuros(partial);
			price.setSellPriceInEuros(partial);
			price.setPriceInEuros(partial);
			price.setPriceDate(from);
			entityManagerProvider.get().persist(price);
			from = DateUtils.addDays(from, 1);
			count = count.add(BigDecimal.ONE);
		}
	}

	/**
	 * Obtiene el ultimo precio calculado para un fondo garantizado previo a una fecha.
	 * 
	 * @param o
	 * @return
	 */
	private AssetPrice findLastPrice(BaseAsset asset, Date before) {
		TypedQuery<AssetPrice> query = entityManagerProvider.get().createNamedQuery("AssetPrice.selectLastByIsin", AssetPrice.class);
		query.setParameter("isin", asset.getIsin()).setParameter("before", before).setMaxResults(1);
		List<AssetPrice> prices = query.getResultList();
		return prices.isEmpty() ? null : prices.iterator().next();
	}

	/**
	 * 
	 * @param o
	 * @param from
	 * @param to
	 * @return
	 */
	public boolean findPricesInRange(BaseAsset o, Date from, Date to) {
		EntityManager entityManager = entityManagerProvider.get();
		TypedQuery<Long> query = entityManager.createQuery("select count(1) from AssetPrice a where a.asset.isin = :isin and a.priceDate >= :from and a.priceDate <= :to",
				Long.class);
		return query.setParameter("isin", o.getId()).setParameter("from", from).setParameter("to", to).getSingleResult() != 0L;
	}

}
