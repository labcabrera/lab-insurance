package org.lab.insurance.model.jpa.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.lab.insurance.model.HasIdentifier;
import org.lab.insurance.model.HasName;
import org.lab.insurance.model.jpa.system.TriggerDefinition;

@Entity
@Table(name = "PRD_AGREEMENT_SERVICE_INFO")
@SuppressWarnings("serial")
@NamedQueries({ @NamedQuery(name = "AgreementServiceInfo.selectByName", query = "select e from AgreementServiceInfo e where e.name = :name") })
public class AgreementServiceInfo implements Serializable, HasIdentifier<String>, HasName {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne
	@JoinColumn(name = "FEES_TRIGGER_DEFINITION")
	private TriggerDefinition feesTriggerDefinition;

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

	public TriggerDefinition getFeesTriggerDefinition() {
		return feesTriggerDefinition;
	}

	public void setFeesTriggerDefinition(TriggerDefinition feesTriggerDefinition) {
		this.feesTriggerDefinition = feesTriggerDefinition;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
