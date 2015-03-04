package org.lab.insurance.model.jpa.contract;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.lab.insurance.model.HasAsset;
import org.lab.insurance.model.HasIdentifier;
import org.lab.insurance.model.jpa.insurance.BaseAsset;

@Entity
@Table(name = "C_FINANCIAL_SERVICE_DISTRIBUTION")
@SuppressWarnings("serial")
public class FinancialServiceDistribution implements Serializable, HasIdentifier<String>, HasAsset {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ASSET_ID", nullable = false)
	private BaseAsset asset;

	@Column(name = "ACTIVATION_PERCENT", precision = 10, scale = 5)
	private BigDecimal activationPercent;

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

	public void setAsset(BaseAsset asset) {
		this.asset = asset;
	}

	public BigDecimal getActivationPercent() {
		return activationPercent;
	}

	public void setActivationPercent(BigDecimal activationPercent) {
		this.activationPercent = activationPercent;
	}
}
