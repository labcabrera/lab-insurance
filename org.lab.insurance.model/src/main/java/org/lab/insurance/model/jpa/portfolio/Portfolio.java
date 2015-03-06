package org.lab.insurance.model.jpa.portfolio;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Representa una cartera de activos. Cada portfolio tiene una serie de {@link Investment} para cada uno de los fondos.
 */
@Entity
@Table(name = "PFL_PORTFOLIO")
@SuppressWarnings("serial")
public class Portfolio implements Serializable {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "NAME", length = 64)
	private String name;

	@Column(name = "TYPE", length = 16)
	@Enumerated(EnumType.STRING)
	private PortfolioType type;

	@OneToMany(mappedBy = "portfolio", fetch = FetchType.LAZY)
	private List<Investment> investments;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PortfolioType getType() {
		return type;
	}

	public void setType(PortfolioType type) {
		this.type = type;
	}

	public List<Investment> getInvestments() {
		return investments;
	}

	public void setInvestments(List<Investment> investments) {
		this.investments = investments;
	}
}
