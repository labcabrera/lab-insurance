package org.lab.insurance.bootstrap.feeders;

import net.sf.flatpack.DataSet;

import org.lab.insurance.model.jpa.product.AgreementValidationInfo;

public class AgreementValidationInfoFeeder extends AbstractEntityFeeder<AgreementValidationInfo> {

	@Override
	protected String getResourceName() {
		return "./agreement-validation-info.csv";
	}

	@Override
	protected AgreementValidationInfo buildEntity(DataSet dataSet) {
		AgreementValidationInfo entity = new AgreementValidationInfo();
		entity.setName(dataSet.getString("NAME"));
		return entity;
	}

}
