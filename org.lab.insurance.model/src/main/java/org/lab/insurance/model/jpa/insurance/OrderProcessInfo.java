package org.lab.insurance.model.jpa.insurance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

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
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Enumerated(EnumType.STRING)
	@Column(name = "SELL_STRATEGY")
	private SellStrategy sellStrategy;

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
}
