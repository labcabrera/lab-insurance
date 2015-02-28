package org.lab.insurance.bootstrap.feeders;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;

import net.sf.flatpack.DataSet;

import org.joda.time.DateTime;
import org.lab.insurance.model.jpa.insurance.AssetPrice;
import org.lab.insurance.model.jpa.insurance.BaseAsset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AssetPriceFeeder extends AbstractFeeder {

	private static final Logger LOG = LoggerFactory.getLogger(AssetPriceFeeder.class);
	private static final String RESOURCE_NAME = "./mock-prices.csv";

	@Override
	public void run() {
		try {
			DataSet dataSet = buildParser(RESOURCE_NAME);
			EntityManager entityManager = entityManagerProvider.get();
			Long t0 = System.currentTimeMillis();
			Long count = 0L;
			while (dataSet.next()) {
				BaseAsset asset = loadBaseAssetFromIsin(dataSet.getString("ISIN"));
				Date from = parseDate(dataSet.getString("DATE_FROM"));
				Date to = parseDate(dataSet.getString("DATE_TO"));
				BigDecimal initialPrice = new BigDecimal(dataSet.getString("INITIAL"));
				BigDecimal variation = new BigDecimal(dataSet.getString("DAILY_VARIATION"));
				loadPrices(asset, from, to, initialPrice, variation, entityManager);
				count++;
			}
			entityManager.flush();
			LOG.info("Loaded mock prices for {} assets in {} ms from file {}", count, (System.currentTimeMillis() - t0), RESOURCE_NAME);
		} catch (Exception ex) {
			throw new RuntimeException("Error loading mock prices", ex);
		}
	}

	private void loadPrices(BaseAsset asset, Date from, Date to, BigDecimal initialPrice, BigDecimal variation, EntityManager entityManager) {
		DateTime dtFrom = new DateTime(from);
		DateTime dtTo = new DateTime(to);
		BigDecimal currentPrice = initialPrice;
		Date now = Calendar.getInstance().getTime();
		while (dtFrom.isBefore(dtTo)) {
			AssetPrice price = new AssetPrice();
			price.setAsset(asset);
			price.setPriceDate(dtFrom.toDate());
			price.setBuyPriceInEuros(currentPrice);
			price.setSellPriceInEuros(currentPrice);
			price.setPriceInEuros(currentPrice);
			price.setGenerated(now);
			entityManager.persist(price);
			currentPrice = currentPrice.add(variation);
			dtFrom = dtFrom.plusDays(1);
		}
	}
}
