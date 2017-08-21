package org.lab.insurance.bootstrap.feeders;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import net.sf.flatpack.DataSet;

import org.lab.insurance.model.jpa.insurance.AssetType;
import org.lab.insurance.model.jpa.insurance.BaseAsset;
import org.lab.insurance.model.jpa.insurance.Currency;

public class BaseAssetFeeder extends AbstractEntityFeeder<BaseAsset> {

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	@Override
	protected String getResourceName() {
		return "./assets.csv";
	}

	@Override
	protected BaseAsset buildEntity(DataSet dataSet) {
		EntityManager entityManager = entityManagerProvider.get();
		BaseAsset asset = new BaseAsset();
		asset.setName(dataSet.getString("NAME"));
		asset.setIsin(dataSet.getString("ISIN"));
		asset.setDecimals(dataSet.getInt("DECIMALS"));
		asset.setType(AssetType.valueOf(dataSet.getString("TYPE")));
		asset.setFromDate(parseDate(dataSet.getString("FROM_DATE")));
		asset.setCurrency(entityManager.find(Currency.class, dataSet.getString("CURRENCY_ID")));
		return asset;
	}
}
