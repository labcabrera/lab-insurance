package org.lab.insurance.bootstrap.feeders;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import net.sf.flatpack.DataSet;

import org.lab.insurance.model.jpa.product.AgreementServiceInfo;
import org.lab.insurance.model.jpa.system.TriggerDefinition;

import com.google.inject.Provider;

public class AgreementServiceInfoFeeder extends AbstractEntityFeeder<AgreementServiceInfo> {

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	@Override
	protected String getResourceName() {
		return "./agreement-service-info.csv";
	}

	@Override
	protected AgreementServiceInfo buildEntity(DataSet dataSet) {
		AgreementServiceInfo entity = new AgreementServiceInfo();
		entity.setName(dataSet.getString("NAME"));
		entity.setFeesTriggerDefinition(findTriggerDefinitionByName(dataSet.getString("TRIGGER_FEES_ID")));
		return entity;
	}

	private TriggerDefinition findTriggerDefinitionByName(String name) {
		EntityManager entityManager = entityManagerProvider.get();
		TypedQuery<TriggerDefinition> query = entityManager.createNamedQuery("TriggerDefinition.selectByName", TriggerDefinition.class);
		return query.setParameter("name", name).getSingleResult();
	}
}
