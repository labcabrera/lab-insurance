package org.lab.insurance.model.jpa.contract;

public enum TriggerType {

	/** Expresion CRON de Quartz2 */
	CRON,

	/**
	 * Se ejecuta el dia X de cada mes independientemente de si es laborable o no.
	 */
	FIXED_MONTLY,

	/**
	 * Se ejecuta el dia X de cada mes. Si ese dia no es laborable se utiliza el siguiente dia laborable.
	 */
	FIXED_LABORAL_DAY_MONTLY_FORWARD,

	/**
	 * Se ejecuta el dia X de cada mes. Si ese dia no es laborable se utiliza el dia laborable anterior.
	 */
	FIXED_LABORAL_DAY_MONTLY_BACKWARD,

	/**
	 * Se ejecuta un determinado dia del año. Si ese dia no es laborable se utiliza el siguiente dia laborable.
	 */
	FIXED_LABORAL_DAY_YEARLY_FORWARD,

	/**
	 * Se ejecuta un determinado dia del año. Si ese dia no es laborable se utiliza el dia laborable anterior.
	 */
	FIXED_LABORAL_DAY_YEARLY_BACKWARD,

	/**
	 * Ultimo dia laborable de cada mes
	 */
	LAST_LABORAL_DAY_OF_MONTH,

	/**
	 * No se realizan activaciones de este servicio.
	 */
	NONE

}
