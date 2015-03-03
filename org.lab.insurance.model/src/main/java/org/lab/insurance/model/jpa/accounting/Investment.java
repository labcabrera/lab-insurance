package org.lab.insurance.model.jpa.accounting;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

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
@NamedQueries({
		@NamedQuery(name = "Investment.selectAtDateByAsset", query = "select e from Investment e where e.portfolio = :portfolio and e.asset = :asset and e.startDate <= :date and (e.endDate is null or e.endDate <= :date)"),
		@NamedQuery(name = "Investment.selectAtDate", query = "select e from Investment e where e.portfolio = :portfolio and e.startDate <= :date and (e.endDate is null or e.endDate <= :date)") })
public class Investment implements Serializable, HasPortfolio, HasState<String> {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne
	@JoinColumn(name = "PORTFOLIO_ID", nullable = false)
	private Portfolio portfolio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSET_ID", nullable = false)
	private BaseAsset asset;

	@ManyToOne
	@JoinColumn(name = "CURRENT_STATE_ID")
	private State currentState;

	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;

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

	public BaseAsset getAsset() {
		return asset;
	}

	public void setAsset(BaseAsset asset) {
		this.asset = asset;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<PortfolioOperation> getEntradas() {
		return entradas;
	}

	public void setEntradas(List<PortfolioOperation> entradas) {
		this.entradas = entradas;
	}

	public List<PortfolioOperation> getSalidas() {
		return salidas;
	}

	public void setSalidas(List<PortfolioOperation> salidas) {
		this.salidas = salidas;
	}
}
