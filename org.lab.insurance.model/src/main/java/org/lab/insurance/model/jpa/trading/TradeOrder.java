package org.lab.insurance.model.jpa.trading;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.lab.insurance.model.HasState;
import org.lab.insurance.model.jpa.engine.State;

@Entity
@Table(name = "T_TRADE_ORDER")
public class TradeOrder implements HasState<String> {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "ORDER_DATE")
	@Temporal(TemporalType.DATE)
	private Date orderDate;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tradeOrder")
	private List<TradeOrderDetail> details;

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

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public List<TradeOrderDetail> getDetails() {
		return details;
	}

	public void setDetails(List<TradeOrderDetail> details) {
		this.details = details;
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
