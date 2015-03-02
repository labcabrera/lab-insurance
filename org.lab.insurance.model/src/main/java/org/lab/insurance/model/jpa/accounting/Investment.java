package org.lab.insurance.model.jpa.accounting;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.lab.insurance.model.HasPortfolio;
import org.lab.insurance.model.HasState;
import org.lab.insurance.model.jpa.engine.State;
import org.lab.insurance.model.jpa.insurance.BaseAsset;

/**
 * Representa una inversion sobre un determinado fondo para una cartera dada.<br>
 * Esta entidad nos permite por ejemplo controlar cuando una cartera ha vendido completamente un fondo y posteriormente ha vuelto a
 * comprarlo (en cuyo caso tenemos el mismo Portfolio pero con diferentes Investments).
 */
@Entity
@Table(name = "I_INVESTMENT")
@SuppressWarnings("serial")
public class Investment implements Serializable, HasPortfolio, HasState<String> {

	@Id
	@Column(name = "ID")
	private String id;

	@ManyToOne
	@JoinColumn(name = "PORTFOLIO_ID")
	private Portfolio portfolio;

	@ManyToOne
	@JoinColumn(name = "ASSET_ID")
	private BaseAsset asset;

	@ManyToOne
	@JoinColumn(name = "CURRENT_STATE_ID")
	private State currentState;

	/**
	 * NOTA: no me gusta tener este mapeo porque tenemos poco control cuando existen un gran volumen de apuntes contables.
	 */
	@OneToMany(mappedBy = "haber", fetch = FetchType.LAZY)
	private List<PortfolioOperation> entradas;

	/**
	 * NOTA: no me gusta tener este mapeo porque tenemos poco control cuando existen un gran volumen de apuntes contables.
	 */
	@OneToMany(mappedBy = "debe", fetch = FetchType.LAZY)
	private List<PortfolioOperation> salidas;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public Portfolio getPortfolio() {
		return portfolio;
	}

	public void setPortfolio(Portfolio portfolio) {
		this.portfolio = portfolio;
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
