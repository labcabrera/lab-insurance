package org.lab.insurance.model.jpa.accounting;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "A_PORTFOLIO_MATH_PROVISION")
public class PortfolioMathProvision {

	@Id
	@Column(name = "ID")
	private String id;

	/**
	 * NOTA: este valor no deja de ser el agregado de los mathProvisions, pero por temas de rendimiento es beficioso tenerlo calculado.
	 */
	@Column(name = "VALUE")
	private BigDecimal value;

	@Column(name = "VALUE_DATE")
	@Temporal(TemporalType.DATE)
	private Date valueDate;

	@Column(name = "GENERATED")
	@Temporal(TemporalType.DATE)
	private Date generated;

	@OneToMany(mappedBy = "portfolioMathProvision")
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
}
