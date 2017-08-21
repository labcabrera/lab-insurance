package org.lab.insurance.model.insurance;

public enum OrderBuyStrategy {

	/**
	 * Por porcentaje de cada {@link OrderDistribution}
	 */
	BY_PERCENT,

	/**
	 * Proporcional a la posicion matematica.
	 */
	PROPORTIONAL_TO_MP;

}
