package org.lab.insurance.model.jpa;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.lab.insurance.model.jpa.accounting.Portfolio;

@Entity
@Table(name = "C_POLICY_PORTFOLIO_INFO")
public class PolicyPorfolioInfo {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PORTFOLIO_PASIVO_ID")
	private Portfolio portfolioPasivo;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "PORTFOLIO_ACTIVO_ID")
	private Portfolio portfolioActivo;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Portfolio getPortfolioPasivo() {
		return portfolioPasivo;
	}

	public void setPortfolioPasivo(Portfolio portfolioPasivo) {
		this.portfolioPasivo = portfolioPasivo;
	}

	public Portfolio getPortfolioActivo() {
		return portfolioActivo;
	}

	public void setPortfolioActivo(Portfolio portfolioActivo) {
		this.portfolioActivo = portfolioActivo;
	}
}
