package org.lab.insurance.bootstrap.feeders;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import net.sf.flatpack.DataSet;

import org.apache.commons.lang3.StringUtils;
import org.lab.insurance.core.serialization.Serializer;
import org.lab.insurance.model.jpa.contract.TriggerDefinition;
import org.lab.insurance.model.jpa.contract.TriggerType;

public class TriggerFeeder extends AbstractEntityFeeder<TriggerDefinition> {

	@Inject
	private Serializer serializer;

	@Override
	protected String getResourceName() {
		return "./triggers.csv";
	}

	@Override
	@SuppressWarnings("unchecked")
	protected TriggerDefinition buildEntity(DataSet dataSet) {
		TriggerDefinition entity = new TriggerDefinition();
		entity.setName(dataSet.getString("NAME"));
		entity.setType(TriggerType.valueOf(dataSet.getString("TYPE")));
		String paramsJson = dataSet.getString("PARAMS_JSON");
		if (StringUtils.isNotBlank(paramsJson)) {
			Map<String, String> readed = serializer.fromJson(paramsJson, Map.class);
			Map<String, String> values = new HashMap<String, String>();
			for (String key : readed.keySet()) {
				values.put(key, readed.get(key));
			}
			entity.setValues(values);
		}
		return entity;
	}
}
