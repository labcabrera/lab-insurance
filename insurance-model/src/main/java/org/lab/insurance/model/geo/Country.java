package org.lab.insurance.model.geo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "GEO_COUNTRY")
@SuppressWarnings("serial")
@NamedQueries({ @NamedQuery(name = "Country.selectByIso3", query = "select e from Country e where e.iso3 = :iso3") })
public class Country implements Serializable {

	/**
	 * El ID sera el ISO2 del pais.
	 */
	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "NAME")
	private String name;

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

	public String getIso3() {
		return iso3;
	}

	public void setIso3(String iso3) {
		this.iso3 = iso3;
	}
}
