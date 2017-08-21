package org.lab.insurance.bootstrap.feeders;

import org.lab.insurance.model.engine.StateDefinition;

import net.sf.flatpack.DataSet;

public class StateDefinitionFeeder extends AbstractEntityFeeder<StateDefinition> {

	@Override
	protected String getResourceName() {
		return "./state-definitions.csv";
	}

	@Override
	protected StateDefinition buildEntity(DataSet dataSet) {
		try {
			StateDefinition entity = new StateDefinition();
			entity.setId(dataSet.getString("ID"));
			entity.setName(dataSet.getString("NAME"));
			entity.setEntityClass(Thread.currentThread().getContextClassLoader().loadClass(dataSet.getString("CLASS")));
			return entity;
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}
}
