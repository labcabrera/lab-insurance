package org.lab.insurance.model.jpa.accounting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.lab.insurance.model.jpa.insurance.BaseAsset;
import org.lab.insurance.model.jpa.insurance.MarketOrder;

/**
 * Representa un asiento contable entre dos Investments.
 */
@Entity
@Table(name = "A_PORTFOLIO_OPERATION")
@SuppressWarnings("serial")
public class PortfolioOperation implements Serializable {

	@Id
	@Column(name = "ID")
	private String id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "DEBE_INVESTMENT_ID", nullable = false)
	private Investment debe;

	@ManyToOne(optional = false)
	@JoinColumn(name = "HABER_INVESTMENT_ID", nullable = false)
	private Investment haber;

	@ManyToOne
	@JoinColumn(name = "ASSET_ID")
	private BaseAsset asset;

	@Column(name = "VALUE_DATE")
	@Temporal(TemporalType.DATE)
	private Date valueDate;

	@Column(name = "UNITS")
	private BigDecimal units;

	@Column(name = "AMOUNT")
	private BigDecimal amount;

	@ManyToOne(optional = false)
	@JoinColumn(name = "MARKET_ORDER_ID", nullable = false)
	private MarketOrder marketOrder;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Investment getDebe() {
		return debe;
	}

	public void setDebe(Investment debe) {
		this.debe = debe;
	}

	public Investment getHaber() {
		return haber;
	}

	public void setHaber(Investment haber) {
		this.haber = haber;
	}

	public BaseAsset getAsset() {
		return asset;
	}

	public void setAsset(BaseAsset asset) {
		this.asset = asset;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public BigDecimal getUnits() {
		return units;
	}

	public void setUnits(BigDecimal units) {
		this.units = units;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public MarketOrder getMarketOrder() {
		return marketOrder;
	}

	public void setMarketOrder(MarketOrder marketOrder) {
		this.marketOrder = marketOrder;
	}
}
