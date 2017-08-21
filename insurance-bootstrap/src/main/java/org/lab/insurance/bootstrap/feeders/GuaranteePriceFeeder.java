package org.lab.insurance.bootstrap.feeders;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import net.sf.flatpack.DataSet;

import org.lab.insurance.bootstrap.mock.AssetPriceFeeder;
import org.lab.insurance.engine.ActionExecutionService;
import org.lab.insurance.engine.model.prices.GuaranteePriceCreationAction;
import org.lab.insurance.model.insurance.BaseAsset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuaranteePriceFeeder extends AbstractFeeder {

	private static final Logger LOG = LoggerFactory.getLogger(AssetPriceFeeder.class);
	private static final String RESOURCE_NAME = "./guarantee-percents.csv";

	@Inject
	private ActionExecutionService actionExecutionService;

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
				BigDecimal percent = new BigDecimal(dataSet.getString("PERCENT"));
				GuaranteePriceCreationAction action = new GuaranteePriceCreationAction();
				action.setAsset(asset);
				action.setFrom(from);
				action.setTo(to);
				action.setPercent(percent);
				actionExecutionService.execute(action);
				count++;
			}
			entityManager.flush();
			LOG.info("Loaded guarantee prices for {} assets in {} ms from file {}", count, (System.currentTimeMillis() - t0), RESOURCE_NAME);
		} catch (Exception ex) {
			throw new RuntimeException("Error loading mock prices", ex);
		}
	}
}
