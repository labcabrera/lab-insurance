package org.lab.insurance.bootstrap.feeders;

import net.sf.flatpack.DataSet;

import org.lab.insurance.model.jpa.insurance.Currency;

public class CurrencyFeeder extends AbstractEntityFeeder<Currency> {

	@Override
	protected String getResourceName() {
		return "./currencies.csv";
	}

	@Override
	protected Currency buildEntity(DataSet dataSet) {
		Currency entity = new Currency();
		entity.setId(dataSet.getString("ID"));
		entity.setName(dataSet.getString("NAME"));
		return entity;
	}

}
