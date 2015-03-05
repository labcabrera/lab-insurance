package org.lab.insurance.bootstrap.feeders;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import net.sf.flatpack.DataSet;

import org.lab.insurance.model.jpa.contract.Agreement;
import org.lab.insurance.model.jpa.contract.AgreementServiceInfo;
import org.lab.insurance.model.jpa.contract.AgreementValidationInfo;

public class AgreementFeeder extends AbstractEntityFeeder<Agreement> {

	@Inject
	private Provider<EntityManager> entityManagerProvider;

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
		entity.setServiceInfo(findAgreementServiceInfoByName(dataSet.getString("SERVICE_INFO_ID")));
		entity.setValidationInfo(findAgreementValidationInfoByName(dataSet.getString("VALIDATION_INFO_ID")));
		return entity;
	}

	private AgreementServiceInfo findAgreementServiceInfoByName(String name) {
		EntityManager entityManager = entityManagerProvider.get();
		TypedQuery<AgreementServiceInfo> query = entityManager.createNamedQuery("AgreementServiceInfo.selectByName", AgreementServiceInfo.class);
		return query.setParameter("name", name).getSingleResult();
	}

	private AgreementValidationInfo findAgreementValidationInfoByName(String name) {
		EntityManager entityManager = entityManagerProvider.get();
		TypedQuery<AgreementValidationInfo> query = entityManager.createNamedQuery("AgreementValidationInfo.selectByName", AgreementValidationInfo.class);
		return query.setParameter("name", name).getSingleResult();
	}

}
