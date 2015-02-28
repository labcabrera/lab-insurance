package org.lab.insurance.bootstrap.feeders;

import net.sf.flatpack.DataSet;

import org.lab.insurance.model.jpa.insurance.AssetType;
import org.lab.insurance.model.jpa.insurance.BaseAsset;

public class BaseAssetFeeder extends AbstractEntityFeeder<BaseAsset> {

	@Override
	protected String getResourceName() {
		return "./assets.csv";
	}

	@Override
	protected BaseAsset buildEntity(DataSet dataSet) {
		BaseAsset asset = new BaseAsset();
		asset.setName(dataSet.getString("NAME"));
		asset.setIsin(dataSet.getString("ISIN"));
		asset.setDecimals(dataSet.getInt("DECIMALS"));
		asset.setType(AssetType.valueOf(dataSet.getString("TYPE")));
		asset.setFromDate(parseDate(dataSet.getString("FROM_DATE")));
		return asset;
	}

}
