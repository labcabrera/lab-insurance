package org.lab.insurance.model.jpa.insurance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.lab.insurance.model.HasPolicy;
import org.lab.insurance.model.HasState;
import org.lab.insurance.model.common.NotSerializable;
import org.lab.insurance.model.jpa.Policy;
import org.lab.insurance.model.jpa.engine.State;

/**
 * Representa un movimiento u operacion de entrada/salida de fondos en un contrato.
 */
@Entity
@Table(name = "I_ORDER")
@SuppressWarnings("serial")
public class Order implements Serializable, HasPolicy, HasState<String> {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE", nullable = false)
	private OrderType type;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH }, optional = false)
	@JoinColumn(name = "POLICY_ID", nullable = false)
	@NotSerializable
	private Policy policy;

	@Embedded
	private OrderDates dates;

	@Column(name = "GROSS_AMOUNT", nullable = false)
	private BigDecimal grossAmount;

	@Column(name = "NET_AMOUNT")
	private BigDecimal netAmount;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
	@JoinTable(name = "I_ORDER_DISTRIBUTION_BUY", joinColumns = { @JoinColumn(name = "ORDER_ID", referencedColumnName = "ID") }, inverseJoinColumns = @JoinColumn(name = "DISTRIBUTION_ID", referencedColumnName = "ID"))
	private List<OrderDistribution> buyDistribution;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
	@JoinTable(name = "I_ORDER_DISTRIBUTION_SELL", joinColumns = { @JoinColumn(name = "ORDER_ID", referencedColumnName = "ID") }, inverseJoinColumns = @JoinColumn(name = "DISTRIBUTION_ID", referencedColumnName = "ID"))
	private List<OrderDistribution> sellDistribution;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private List<MarketOrder> marketOrders;

	@ManyToOne
	@JoinColumn(name = "CURRENT_STATE_ID")
	private State currentState;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private OrderProcessInfo processInfo;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public OrderType getType() {
		return type;
	}

	public void setType(OrderType type) {
		this.type = type;
	}

	@Override
	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public OrderDates getDates() {
		return dates;
	}

	public void setDates(OrderDates dates) {
		this.dates = dates;
	}

	public List<OrderDistribution> getBuyDistribution() {
		return buyDistribution;
	}

	public void setBuyDistribution(List<OrderDistribution> buyDistribution) {
		this.buyDistribution = buyDistribution;
	}

	public List<OrderDistribution> getSellDistribution() {
		return sellDistribution;
	}

	public void setSellDistribution(List<OrderDistribution> sellDistribution) {
		this.sellDistribution = sellDistribution;
	}

	public List<MarketOrder> getMarketOrders() {
		return marketOrders;
	}

	public void setMarketOrders(List<MarketOrder> marketOrders) {
		this.marketOrders = marketOrders;
	}

	@Override
	public State getCurrentState() {
		return currentState;
	}

	@Override
	public void setCurrentState(State currentState) {
		this.currentState = currentState;
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

	public OrderProcessInfo getProcessInfo() {
		return processInfo;
	}

	public void setProcessInfo(OrderProcessInfo processInfo) {
		this.processInfo = processInfo;
	}
}
