package org.lab.insurance.model.jpa.insurance;

/**
 * Enumeracion de los tipos de movimientos existentes.
 */
public enum OrderType {

	INITIAL_PAYMENT, ADDITIONAL_PAYMENT, REGULAR_PAYMENT,

	SWITCH, STOP_LOSS, PROGRESS_INVESTMENT, LOCK_IN,

	PARTIAL_SURRENDER, TOTAL_SURRENDER, REGULAR_SURRENDER,

	FEES

}
