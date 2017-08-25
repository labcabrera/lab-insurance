package org.lab.insurance.model.insurance;

public enum OrderSellStrategy {

	/**
	 * Se vende por importe del order. Cada OrderDistribution tiene su importe.
	 */
	SELL_BY_AMOUNT,

	/**
	 * Se vende por las unidades del OrderDistribution.units.
	 */
	SELL_BY_UNITS,

	/**
	 * Se vende a partir de un porcentaje de UCs de la cartera.
	 */
	SELL_BY_MATH_PROVISION_PERCENT,

	/**
	 * Rescate total.
	 */
	SELL_ALL;

}
