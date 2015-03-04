package org.lab.insurance.model.jpa.contract;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.lab.insurance.model.HasIdentifier;
import org.lab.insurance.model.jpa.accounting.Portfolio;

@Entity
@Table(name = "C_CONTRACT_PORTFOLIO_INFO")
@SuppressWarnings("serial")
public class ContractPorfolioInfo implements Serializable, HasIdentifier<String> {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PORTFOLIO_PASSIVE_ID", nullable = false)
	private Portfolio portfolioPassive;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PORTFOLIO_ACTIVE_ID", nullable = false)
	private Portfolio portfolioActive;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PORTFOLIO_FEES_ID", nullable = false)
	private Portfolio portfolioFees;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public Portfolio getPortfolioPassive() {
		return portfolioPassive;
	}

	public void setPortfolioPassive(Portfolio portfolioPassive) {
		this.portfolioPassive = portfolioPassive;
	}

	public Portfolio getPortfolioActive() {
		return portfolioActive;
	}

	public void setPortfolioActive(Portfolio portfolioActive) {
		this.portfolioActive = portfolioActive;
	}

	public Portfolio getPortfolioFees() {
		return portfolioFees;
	}

	public void setPortfolioFees(Portfolio portfolioFees) {
		this.portfolioFees = portfolioFees;
	}
}
