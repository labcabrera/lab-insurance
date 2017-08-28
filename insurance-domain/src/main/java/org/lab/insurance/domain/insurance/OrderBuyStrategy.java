package org.lab.insurance.domain.insurance;

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
