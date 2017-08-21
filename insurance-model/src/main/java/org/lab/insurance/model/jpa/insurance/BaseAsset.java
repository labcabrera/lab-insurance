package org.lab.insurance.model.jpa.insurance;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "INS_BASE_ASSET")
@NamedQueries({ @NamedQuery(name = "BaseAsset.selectByIsin", query = "select e from BaseAsset e where e.isin= :isin") })
@SuppressWarnings("serial")
// @Index(name = "IX_ISIN", columnList = "isin")
public class BaseAsset implements Serializable {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	/**
	 * Divisa en la que opera el fondo.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID", nullable = false)
	private Currency currency;

	/**
	 * Codigo ISIN del fondo.
	 */
	@Column(name = "ISIN", nullable = false, length = 16)
	private String isin;

	/**
	 * Nombre del fondo.
	 */
	@Column(name = "NAME", nullable = false, length = 256)
	private String name;

	/**
	 * Nombre corto del fondo.
	 */
	@Column(name = "SHORT_NAME", length = 128)
	private String shortName;

	/**
	 * Tipo de fondo.
	 */
	@Column(name = "TYPE", nullable = false, length = 16)
	@Enumerated(EnumType.STRING)
	private AssetType type;

	/**
	 * Numero de decimales con los que opera el fondo.
	 */
	@Column(name = "DECIMALS", nullable = false)
	private Integer decimals;

	@Column(name = "FROM_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date fromDate;

	@Column(name = "TO_DATE")
	@Temporal(TemporalType.DATE)
	private Date toDate;

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

	public AssetType getType() {
		return type;
	}

	public void setType(AssetType type) {
		this.type = type;
	}

	public Date getFromDate() {
		return fromDate;
	}

	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}

	public Date getToDate() {
		return toDate;
	}

	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
}
