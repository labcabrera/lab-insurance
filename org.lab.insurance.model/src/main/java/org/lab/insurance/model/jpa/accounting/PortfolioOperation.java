package org.lab.insurance.model.jpa.accounting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import org.lab.insurance.model.jpa.insurance.BaseAsset;
import org.lab.insurance.model.jpa.insurance.MarketOrder;

/**
 * Representa un asiento contable entre dos Investments.
 */
@Entity
@Table(name = "I_PORTFOLIO_OPERATION")
@SuppressWarnings("serial")
@NamedQueries({ @NamedQuery(name = "PortfolioOperation.unitsIn", query = "select sum(e.units) from PortfolioOperation e where e.haber = :investment and e.valueDate <= :date"),
		@NamedQuery(name = "PortfolioOperation.unitsOut", query = "select sum(e.units) from PortfolioOperation e where e.debe = :investment and e.valueDate <= :date") })
public class PortfolioOperation implements Serializable {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "DEBE_INVESTMENT_ID", nullable = false)
	private Investment debe;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "HABER_INVESTMENT_ID", nullable = false)
	private Investment haber;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "ASSET_ID", nullable = false)
	private BaseAsset asset;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "MARKET_ORDER_ID", nullable = false)
	private MarketOrder marketOrder;

	@Column(name = "VALUE_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date valueDate;

	@Column(name = "UNITS", nullable = false, precision = 20, scale = 7)
	private BigDecimal units;

	@Column(name = "AMOUNT", nullable = false, precision = 20, scale = 7)
	private BigDecimal amount;

	@Column(name = "DESCRIPTION", length = 516)
	private String description;

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
