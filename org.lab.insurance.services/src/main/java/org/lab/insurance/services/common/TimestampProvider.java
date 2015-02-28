package org.lab.insurance.services.common;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Singleton;

/**
 * Entidad que nos permite cambiar la fecha del sistema para trabajar a futuro o a pasado.
 */
@Singleton
public class TimestampProvider {

	// TODO
	/**
	 * Devuelve la fecha actual truncada a las 00:00:00 horas.
	 * 
	 * @return
	 */
	public Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

	// TODO
	/**
	 * Devuelve la fecha actual.
	 * 
	 * @return
	 */
	public Date getCurrentDateTime() {
		return Calendar.getInstance().getTime();
	}

}
