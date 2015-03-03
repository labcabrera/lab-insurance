package org.lab.insurance.model.jpa.accounting;

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

@Entity
@Table(name = "I_MATH_PROVISION")
@SuppressWarnings("serial")
public class MathProvision implements Serializable {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PORTFOLIO_MATH_PROVISION_ID", nullable = false)
	private PortfolioMathProvision portfolioMathProvision;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INVESTMENT_ID", nullable = false)
	private Investment investment;

	@Column(name = "UNITS", nullable = false)
	private BigDecimal units;

	@Column(name = "AMOUNT", nullable = false)
	private BigDecimal amount;

	@Column(name = "COST", nullable = false)
	private BigDecimal cost;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PortfolioMathProvision getPortfolioMathProvision() {
		return portfolioMathProvision;
	}

	public void setPortfolioMathProvision(PortfolioMathProvision portfolioMathProvision) {
		this.portfolioMathProvision = portfolioMathProvision;
	}

	public Investment getInvestment() {
		return investment;
	}

	public void setInvestment(Investment investment) {
		this.investment = investment;
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

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
}
