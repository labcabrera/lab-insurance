package org.lab.insurance.model.jpa.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "P_PROVINCE")
@SuppressWarnings("serial")
public class Province implements Serializable {

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String name;

	@ManyToOne
	@JoinColumn(name = "COUNTRY_ID")
	private Country country;

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

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}
}
