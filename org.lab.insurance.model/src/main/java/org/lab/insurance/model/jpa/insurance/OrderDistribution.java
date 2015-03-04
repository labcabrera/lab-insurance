package org.lab.insurance.model.jpa.insurance;

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

/**
 * Entidad que representa cada uno de los elementos que forman parte de la definicion de entradas o salidas de un movimiento.
 */
@Entity
@Table(name = "I_ORDER_DISTRIBUTION")
@SuppressWarnings("serial")
public class OrderDistribution implements Serializable {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSET_ID", nullable = false)
	private BaseAsset asset;

	@Column(name = "AMOUNT", precision = 20, scale = 7)
	private BigDecimal amount;

	@Column(name = "PERCENT", precision = 20, scale = 7)
	private BigDecimal percent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BaseAsset getAsset() {
		return asset;
	}

	public void setAsset(BaseAsset asset) {
		this.asset = asset;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public OrderDistribution withAsset(BaseAsset asset) {
		this.asset = asset;
		return this;
	}

	public OrderDistribution withPercent(BigDecimal percent) {
		this.percent = percent;
		return this;
	}

}
