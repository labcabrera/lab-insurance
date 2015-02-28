package org.lab.insurance.model.jpa.insurance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Entidad que nos proporciona el tipo garantizado en un intervalo de tiempo.
 */
@Entity
@Table(name = "I_ASSET_GUARANTEE")
@SuppressWarnings("serial")
public class AssetGuaranteePercent implements Serializable {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "ASSET_ID", nullable = false)
	private BaseAsset baseAsset;

	@Column(name = "FROM_DATE", nullable = false)
	private Date fromDate;

	@Column(name = "TO_DATE", nullable = false)
	private Date toDate;

	@Column(name = "GUARANTEE_PERCENT", nullable = false)
	private BigDecimal guaranteePercent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BaseAsset getBaseAsset() {
		return baseAsset;
	}

	public void setBaseAsset(BaseAsset baseAsset) {
		this.baseAsset = baseAsset;
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

	public BigDecimal getGuaranteePercent() {
		return guaranteePercent;
	}

	public void setGuaranteePercent(BigDecimal guaranteePercent) {
		this.guaranteePercent = guaranteePercent;
	}
}
