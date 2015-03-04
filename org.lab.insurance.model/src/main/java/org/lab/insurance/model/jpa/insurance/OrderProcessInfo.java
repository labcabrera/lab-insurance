package org.lab.insurance.model.jpa.insurance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.lab.insurance.model.jpa.accounting.Portfolio;

/**
 * Entidad que contiene informacion interna acerca de como se va a gestionar la orden (por ejemplo como se aplican los gastos, como se
 * realiza la venta de fondos, etc).
 */
@Entity
@Table(name = "I_ORDER_PROCESS_INFO")
@SuppressWarnings("serial")
public class OrderProcessInfo implements Serializable {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Enumerated(EnumType.STRING)
	@Column(name = "SELL_STRATEGY")
	private SellStrategy sellStrategy;

	/**
	 * En caso de que este valor sea distinto de nulo nos permite sobreescribir el modo en el que se contabiliza la operacion en el pasivo.
	 * Si es nulo obtendra el valor del contrato.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PORTFOLIO_PASSIVE_ID")
	private Portfolio portfolioPassive;

	/**
	 * En caso de que este valor sea distinto de nulo nos permite sobreescribir el modo en el que se contabiliza la operacion en el activo.
	 * Si es nulo obtendra el valor del contrato.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PORTFOLIO_ACTIVE_ID")
	private Portfolio portfolioActive;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SellStrategy getSellStrategy() {
		return sellStrategy;
	}

	public void setSellStrategy(SellStrategy sellStrategy) {
		this.sellStrategy = sellStrategy;
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

}
