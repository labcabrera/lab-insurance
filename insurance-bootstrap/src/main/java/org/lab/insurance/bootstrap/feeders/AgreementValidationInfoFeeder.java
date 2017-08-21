package org.lab.insurance.bootstrap.feeders;

import org.lab.insurance.model.product.AgreementValidationInfo;

import net.sf.flatpack.DataSet;

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
