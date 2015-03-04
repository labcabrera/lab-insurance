package org.lab.insurance.bootstrap.feeders;

import net.sf.flatpack.DataSet;

import org.lab.insurance.model.jpa.contract.Agreement;

public class AgreementFeeder extends AbstractEntityFeeder<Agreement> {

	@Override
	protected String getResourceName() {
		return "./agreements.csv";
	}

	@Override
	protected Agreement buildEntity(DataSet dataSet) {
		Agreement entity = new Agreement();
		entity.setCode(dataSet.getString("CODE"));
		entity.setName(dataSet.getString("NAME"));
		entity.setStartDate(parseDate(dataSet.getString("START_DATE")));
		return entity;
	}

}
