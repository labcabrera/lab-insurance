package org.lab.insurance.model.jpa.legalentity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.lab.insurance.model.HasCode;
import org.lab.insurance.model.HasIdentifier;
import org.lab.insurance.model.HasName;

@Entity
@Table(name = "LGE_PROFESSION")
@SuppressWarnings("serial")
public class Profession implements Serializable, HasIdentifier<String>, HasName, HasCode {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "NAME", length = 128, nullable = false)
	private String name;

	@Column(name = "CODE", length = 128)
	private String code;

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

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
