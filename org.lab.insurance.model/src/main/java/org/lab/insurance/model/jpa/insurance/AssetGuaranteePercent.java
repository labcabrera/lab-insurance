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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.lab.insurance.model.HasAsset;
import org.lab.insurance.model.HasIdentifier;

/**
 * Entidad que nos proporciona el tipo garantizado en un intervalo de tiempo.
 */
@Entity
@Table(name = "I_ASSET_GUARANTEE")
@SuppressWarnings("serial")
public class AssetGuaranteePercent implements HasIdentifier<String>, HasAsset, Serializable {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
	@JoinColumn(name = "ASSET_ID", nullable = false)
	private BaseAsset asset;

	@Column(name = "FROM_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date from;

	@Column(name = "TO_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date to;

	@Column(name = "GUARANTEE_PERCENT", nullable = false, precision = 20, scale = 7)
	private BigDecimal guaranteePercent;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public BaseAsset getAsset() {
		return asset;
	}

	public void setAsset(BaseAsset baseAsset) {
		this.asset = baseAsset;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date fromDate) {
		this.from = fromDate;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date toDate) {
		this.to = toDate;
	}

	public BigDecimal getGuaranteePercent() {
		return guaranteePercent;
	}

	public void setGuaranteePercent(BigDecimal guaranteePercent) {
		this.guaranteePercent = guaranteePercent;
	}
}
