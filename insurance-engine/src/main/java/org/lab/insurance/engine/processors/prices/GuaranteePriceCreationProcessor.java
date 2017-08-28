package org.lab.insurance.engine.processors.prices;

import static org.apache.commons.lang3.time.DateFormatUtils.ISO_DATE_FORMAT;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.time.DateUtils;
import org.lab.insurance.core.math.BigMath;
import org.lab.insurance.domain.insurance.AssetPrice;
import org.lab.insurance.domain.insurance.Asset;
import org.lab.insurance.domain.insurance.Currency;
import org.lab.insurance.domain.insurance.repository.AssetPriceRepository;
import org.lab.insurance.domain.insurance.repository.CurrencyRepository;
import org.lab.insurance.engine.model.prices.GuaranteePriceCreationAction;
import org.lab.insurance.services.insurance.CotizationsService;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

/**
 * Procesador encargado de generar los precios de un tipo garantizado.
 * 
 * @see GuaranteePriceCreationValidator
 */
@Slf4j
public class GuaranteePriceCreationProcessor implements Processor {

	@Autowired
	private AssetPriceRepository assetPriceRepository;
	@Autowired
	private CotizationsService cotizationsService;
	@Autowired
	private CurrencyRepository currencyRepository;

	@Override
	public void process(Exchange exchange) throws Exception {
		Currency currency = resolveGuaranteeCurrency();
		GuaranteePriceCreationAction action = exchange.getIn().getBody(GuaranteePriceCreationAction.class);
		Asset asset = action.getAsset();
		Date from = action.getFrom();
		Date to = action.getTo();
		BigDecimal guarantee = action.getPercent();
		log.info("Creating guarantee prices for {} at {}% from {} to {}", asset.getName(), guarantee,
				from != null ? ISO_DATE_FORMAT.format(from) : "", ISO_DATE_FORMAT.format(to));
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
			assetPriceRepository.save(price);
			from = DateUtils.addDays(from, 1);
			count = count.add(BigDecimal.ONE);
		}
	}

	/**
	 * NOTA: los precios del el garantizado carecen de divisa (no deja de ser una serie de numeros calculados segun la
	 * formula de revalorizacion) ya que no aunque el modelo obliga a establecerla de modo que la fijamos a EURO.
	 * 
	 * @return
	 */
	private Currency resolveGuaranteeCurrency() {
		return currencyRepository.findByIso("EUR");
	}
}
