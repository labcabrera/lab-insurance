package org.lab.insurance.model.jpa.insurance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "I_BASE_ASSET")
@NamedQueries({ @NamedQuery(name = "BaseAsset.selectByIsin", query = "select e from BaseAsset e where e.isin= :isin") })
@SuppressWarnings("serial")
// @Index(name = "IX_ISIN", columnList = "isin")
public class BaseAsset implements Serializable {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "ISIN")
	private String isin;

	@Column(name = "NAME")
	private String name;

	/** Numero de decimales con los que opera el fondo. */
	@Column(name = "DECIMALS")
	private Integer decimals;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getDecimals() {
		return decimals;
	}

	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}
}
