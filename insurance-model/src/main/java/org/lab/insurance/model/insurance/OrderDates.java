package org.lab.insurance.model.insurance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entidad que representa las fechas de un movimiento (ya sea un Order o un MarketOrder).
 * <ul>
 * <li><b>effective</b>: fecha de efecto del movimiento</li>
 * <li><b>valueDate</b>: fecha de valor del movimiento</li>
 * <li><b>valued</b>: fecha en la que el movimiento es valorizado</li>
 * <li><b>processed</b>: fecha de procesamiento del movimiento</li>
 * <li><b>accounted</b>: fecha en la que el movimiento es contabilizado</li>
 * </ul>
 */
@Embeddable
public class OrderDates {

	@Column(name = "EFFECTIVE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date effective;

	@Column(name = "VALUE_DATE")
	@Temporal(TemporalType.DATE)
	private Date valueDate;

	@Column(name = "VALUED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date valued;

	@Column(name = "PROCESSED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date processed;

	@Column(name = "ACCOUNTED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date accounted;

	public Date getEffective() {
		return effective;
	}

	public void setEffective(Date effective) {
		this.effective = effective;
	}

	public Date getValueDate() {
		return valueDate;
	}

	public void setValueDate(Date valueDate) {
		this.valueDate = valueDate;
	}

	public Date getValued() {
		return valued;
	}

	public void setValued(Date valued) {
		this.valued = valued;
	}

	public Date getProcessed() {
		return processed;
	}

	public void setProcessed(Date processed) {
		this.processed = processed;
	}

	public Date getAccounted() {
		return accounted;
	}

	public void setAccounted(Date accounted) {
		this.accounted = accounted;
	}
}
