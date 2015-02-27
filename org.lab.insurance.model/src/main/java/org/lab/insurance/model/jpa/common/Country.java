package org.lab.insurance.model.jpa.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "C_COUNTRY")
@SuppressWarnings("serial")
public class Country implements Serializable {

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "ISO2")
	private String iso2;

	@Column(name = "ISO3")
	private String iso3;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIso2() {
		return iso2;
	}

	public void setIso2(String iso2) {
		this.iso2 = iso2;
	}

	public String getIso3() {
		return iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}
}
