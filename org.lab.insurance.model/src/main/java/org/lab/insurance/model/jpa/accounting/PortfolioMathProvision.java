package org.lab.insurance.model.jpa.accounting;

import java.math.BigDecimal;
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "I_PORTFOLIO_MATH_PROVISION")
@NamedQueries({ @NamedQuery(name = "PortfolioMathProvision.selectByDate", query = "select e from PortfolioMathProvision e where e.portfolio = :portfolio and e.valueDate = :date") })
public class PortfolioMathProvision {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PORTFOLIO_ID", nullable = false)
	private Portfolio portfolio;

	/**
	 * NOTA: este valor no deja de ser el agregado de los mathProvisions, pero por temas de rendimiento es beficioso tenerlo calculado.
	 */
	@Column(name = "VALUE", nullable = false)
	private BigDecimal value;

	@Column(name = "VALUE_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date valueDate;

	@Column(name = "GENERATED", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date generated;

	@OneToMany(mappedBy = "portfolioMathProvision", cascade = { CascadeType.PERSIST })
	private List<MathProvision> mathProvisions;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public Date getGenerated() {
		return generated;
	}

	public void setGenerated(Date generated) {
		this.generated = generated;
	}

	public List<MathProvision> getMathProvisions() {
		return mathProvisions;
	}

	public void setMathProvisions(List<MathProvision> mathProvisions) {
		this.mathProvisions = mathProvisions;
	}

	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
	}

}
