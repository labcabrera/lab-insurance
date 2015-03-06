package org.lab.insurance.model.jpa.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.lab.insurance.model.HasIdentifier;
import org.lab.insurance.model.HasName;

@Entity
@Table(name = "COM_LANGUAGE")
@SuppressWarnings("serial")
public class Language implements Serializable, HasIdentifier<String>, HasName {

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String name;

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
}
