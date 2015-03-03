package org.lab.insurance.model.jpa.trading;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.lab.insurance.model.HasAsset;
import org.lab.insurance.model.HasState;
import org.lab.insurance.model.jpa.engine.State;
import org.lab.insurance.model.jpa.insurance.BaseAsset;
import org.lab.insurance.model.jpa.insurance.MarketOrderSource;

@Entity
@Table(name = "T_TRADE_ORDER_DETAIL")
public class TradeOrderDetail implements HasState<String>, HasAsset {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TRADE_ORDER_ID", nullable = false)
	private TradeOrder tradeOrder;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BASE_ASSET_ID", nullable = false)
	private BaseAsset asset;

	@Column(name = "MARKET_ORDER_SOURCE", nullable = false)
	@Enumerated(EnumType.STRING)
	private MarketOrderSource source;

	@Column(name = "AMOUNT_SOURCE")
	private BigDecimal amountSource;

	@Column(name = "UNITS_SOURCE")
	private BigDecimal unitsSource;

	@Column(name = "EST_NAV")
	private BigDecimal estimatedNav;

	@Column(name = "EST_AMOUNT")
	private BigDecimal estimatedAmount;

	@Column(name = "EST_UNITS")
	private BigDecimal estimatedUnits;

	@Column(name = "NAV_OUTCOME")
	private BigDecimal navOutcome;

	@Column(name = "AMOUNT_OUTCOME")
	private BigDecimal amountOutcome;

	@Column(name = "UNITS_OUTCOME")
	private BigDecimal unitsOutcome;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENT_STATE_ID")
	private State currentState;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public TradeOrder getTradeOrder() {
		return tradeOrder;
	}

	public void setTradeOrder(TradeOrder tradeOrder) {
		this.tradeOrder = tradeOrder;
	}

	@Override
	public BaseAsset getAsset() {
		return asset;
	}

	public void setAsset(BaseAsset asset) {
		this.asset = asset;
	}

	public MarketOrderSource getSource() {
		return source;
	}

	public void setSource(MarketOrderSource source) {
		this.source = source;
	}

	public BigDecimal getAmountSource() {
		return amountSource;
	}

	public void setAmountSource(BigDecimal amountSource) {
		this.amountSource = amountSource;
	}

	public BigDecimal getUnitsSource() {
		return unitsSource;
	}

	public void setUnitsSource(BigDecimal unitsSource) {
		this.unitsSource = unitsSource;
	}

	public BigDecimal getAmountOutcome() {
		return amountOutcome;
	}

	public void setAmountOutcome(BigDecimal amountOutcome) {
		this.amountOutcome = amountOutcome;
	}

	public BigDecimal getUnitsOutcome() {
		return unitsOutcome;
	}

	public void setUnitsOutcome(BigDecimal unitsOutcome) {
		this.unitsOutcome = unitsOutcome;
	}

	public BigDecimal getEstimatedNav() {
		return estimatedNav;
	}

	public void setEstimatedNav(BigDecimal estimatedNav) {
		this.estimatedNav = estimatedNav;
	}

	public BigDecimal getEstimatedAmount() {
		return estimatedAmount;
	}

	public void setEstimatedAmount(BigDecimal estimatedAmount) {
		this.estimatedAmount = estimatedAmount;
	}

	public BigDecimal getEstimatedUnits() {
		return estimatedUnits;
	}

	public void setEstimatedUnits(BigDecimal estimatedUnits) {
		this.estimatedUnits = estimatedUnits;
	}

	public BigDecimal getNavOutcome() {
		return navOutcome;
	}

	public void setNavOutcome(BigDecimal navOutcome) {
		this.navOutcome = navOutcome;
	}

	@Override
	public State getCurrentState() {
		return currentState;
	}

	@Override
	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}
}
