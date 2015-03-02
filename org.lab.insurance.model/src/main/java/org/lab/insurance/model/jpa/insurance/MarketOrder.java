package org.lab.insurance.model.jpa.insurance;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.lab.insurance.model.HasAsset;
import org.lab.insurance.model.HasOrder;
import org.lab.insurance.model.HasState;
import org.lab.insurance.model.common.NotSerializable;
import org.lab.insurance.model.jpa.engine.State;

@Entity
@Table(name = "I_MARKET_ORDER")
@SuppressWarnings("serial")
public class MarketOrder implements Serializable, HasOrder, HasState<String>, HasAsset {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne
	@JoinColumn(name = "ORDER_ID")
	@NotSerializable
	private Order order;

	@Column(name = "TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private MarketOrderType type;

	@Column(name = "SOURCE", nullable = false)
	@Enumerated(EnumType.STRING)
	private MarketOrderSource source;

	@ManyToOne
	@JoinColumn(name = "ASSET_ID")
	private BaseAsset asset;

	@Embedded
	private OrderDates dates;

	@Column(name = "UNITS")
	private BigDecimal units;

	@Column(name = "GROSS_AMOUNT")
	private BigDecimal grossAmount;

	@Column(name = "NET_AMOUNT")
	private BigDecimal netAmount;

	@Column(name = "NAV")
	private BigDecimal nav;

	@ManyToOne
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

	@Override
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public BaseAsset getAsset() {
		return asset;
	}

	public void setAsset(BaseAsset asset) {
		this.asset = asset;
	}

	public OrderDates getDates() {
		return dates;
	}

	public void setDates(OrderDates dates) {
		this.dates = dates;
	}

	public BigDecimal getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(BigDecimal grossAmount) {
		this.grossAmount = grossAmount;
	}

	public BigDecimal getNetAmount() {
		return netAmount;
	}

	public void setNetAmount(BigDecimal netAmount) {
		this.netAmount = netAmount;
	}

	public MarketOrderType getType() {
		return type;
	}

	public void setType(MarketOrderType type) {
		this.type = type;
	}

	public MarketOrderSource getSource() {
		return source;
	}

	public void setSource(MarketOrderSource source) {
		this.source = source;
	}

	public BigDecimal getUnits() {
		return units;
	}

	public void setUnits(BigDecimal units) {
		this.units = units;
	}

	public BigDecimal getNav() {
		return nav;
	}

	public void setNav(BigDecimal nav) {
		this.nav = nav;
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
