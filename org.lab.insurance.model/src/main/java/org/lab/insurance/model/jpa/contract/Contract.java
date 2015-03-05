package org.lab.insurance.model.jpa.contract;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.lab.insurance.model.HasActivationRange;
import org.lab.insurance.model.HasState;
import org.lab.insurance.model.common.NotSerializable;
import org.lab.insurance.model.jpa.engine.State;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.model.validation.ValidContract;

@Entity
@Table(name = "C_CONTRACT")
@SuppressWarnings("serial")
@ValidContract
public class Contract implements Serializable, HasState<String>, HasActivationRange {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "NUMBER", nullable = false, length = 32)
	private String number;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH })
	@JoinColumn(name = "AGREEMENT_ID", nullable = false)
	@NotSerializable
	private Agreement agreement;

	@Column(name = "EFFECTIVE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date effective;

	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST }, optional = false)
	@JoinColumn(name = "PORTFOLIO_INFO_ID", nullable = false)
	@NotSerializable
	private ContractPorfolioInfo portfolioInfo;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "STATE_ID")
	private State currentState;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contract", cascade = { CascadeType.PERSIST })
	private List<PolicyEntityRelation> relations;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contract", cascade = { CascadeType.PERSIST })
	@NotSerializable
	private List<Order> orders;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Agreement getAgreement() {
		return agreement;
	}

	public void setAgreement(Agreement agreement) {
		this.agreement = agreement;
	}

	public List<PolicyEntityRelation> getRelations() {
		return relations;
	}

	public void setRelations(List<PolicyEntityRelation> relations) {
		this.relations = relations;
	}

	public Date getEffective() {
		return effective;
	}

	public void setEffective(Date effective) {
		this.effective = effective;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public State getCurrentState() {
		return currentState;
	}

	@Override
	public void setCurrentState(State state) {
		currentState = state;
	}

	public ContractPorfolioInfo getPortfolioInfo() {
		return portfolioInfo;
	}

	public void setPortfolioInfo(ContractPorfolioInfo portfolioInfo) {
		this.portfolioInfo = portfolioInfo;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
