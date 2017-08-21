package org.lab.insurance.model.system;

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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.lab.insurance.model.HasIdentifier;
import org.lab.insurance.model.HasName;

/**
 * Representa una condicion de activacion de un proceso. Bien sea una expresion cron, un proceso que se ejecuta el dia X de cada mes o un
 * proceso anual.
 */
@Entity
@Table(name = "SYS_TRIGGER_DEFINITION")
@NamedQueries({ @NamedQuery(name = "TriggerDefinition.selectByName", query = "select e from TriggerDefinition e where e.name = :name") })
@SuppressWarnings("serial")
public class TriggerDefinition implements Serializable, HasIdentifier<String>, HasName {

	public static final String KEY_DAY = "K_DAY";
	public static final String KEY_MONTH = "K_MONTH";
	public static final String KEY_CRON = "K_CRON";

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "PARAM_KEY", length = 516)
	@Column(name = "PARAM_VALUE", length = 64)
	@CollectionTable(name = "SYS_TRIGGER_PARAM", joinColumns = @JoinColumn(name = "TRIGGER_ID"))
	private Map<String, String> values = new HashMap<String, String>();

	@Column(name = "TYPE", length = 32)
	@Enumerated(EnumType.STRING)
	private TriggerType type;

	@Column(name = "NAME", length = 64, nullable = false)
	private String name;

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

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
