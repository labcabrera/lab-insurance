package org.lab.insurance.model.jpa.contract;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import org.lab.insurance.model.HasIdentifier;

/**
 * Representa una condicion de activacion de un proceso. Bien sea una expresion cron, un proceso que se ejecuta el dia X de cada mes o un
 * proceso anual.
 */
@Entity
@Table(name = "C_TRIGGER_DEFINITION")
@SuppressWarnings("serial")
public class TriggerDefinition implements Serializable, HasIdentifier<String> {

	@Id
	@Column(name = "ID", length = 36)
	private String id;

	@Column(name = "TYPE", length = 32)
	@Enumerated(EnumType.STRING)
	private TriggerType type;

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "PARAM_KEY", length = 516)
	@Column(name = "PARAM_VALUE", length = 64)
	@CollectionTable(name = "C_TRIGGER_PARAM", joinColumns = @JoinColumn(name = "TRIGGER_ID"))
	private Map<String, String> values = new HashMap<String, String>();

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public TriggerType getType() {
		return type;
	}

	public void setType(TriggerType type) {
		this.type = type;
	}

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

}
