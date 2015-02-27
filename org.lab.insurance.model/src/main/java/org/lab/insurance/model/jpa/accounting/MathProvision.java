package org.lab.insurance.model.jpa.accounting;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "A_MATH_PROVISION")
@SuppressWarnings("serial")
public class MathProvision implements Serializable {

	@Id
	@Column(name = "ID")
	private String id;

	@ManyToOne
	@JoinColumn(name = "PORTFOLIO_MATH_PROVISION_ID")
	private PortfolioMathProvision portfolioMathProvision;

}
