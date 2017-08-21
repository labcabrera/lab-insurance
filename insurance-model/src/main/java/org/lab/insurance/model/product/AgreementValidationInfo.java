package org.lab.insurance.model.product;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
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

@Entity
@Table(name = "PRD_AGREEMENT_VALIDATION_INFO")
@SuppressWarnings("serial")
@NamedQueries({ @NamedQuery(name = "AgreementValidationInfo.selectByName", query = "select e from AgreementValidationInfo e where e.name = :name") })
public class AgreementValidationInfo implements Serializable, HasIdentifier<String>, HasName {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "NAME", length = 64, nullable = false)
	private String name;

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "PARAM_KEY", length = 516)
	@Column(name = "PARAM_VALUE", length = 64)
	@CollectionTable(name = "PRD_AGREEMENT_VALIDATION_PARAM", joinColumns = @JoinColumn(name = "AGREEMENT_VALIDATION_ID"))
	private Map<String, String> values = new HashMap<String, String>();

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}
}
