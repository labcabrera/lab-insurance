package org.lab.insurance.services.common;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Singleton;

import org.joda.time.DateTime;

/**
 * Entidad que nos permite cambiar la fecha del sistema para trabajar a futuro o a pasado.
 */
@Singleton
public class TimestampProvider {

	private ThreadLocal<Long> offset;

	public TimestampProvider() {
		offset = new ThreadLocal<Long>();
		offset.set(0L);
	}

	/**
	 * Devuelve la fecha actual truncada a las 00:00:00 horas.
	 * 
	 * @return
	 */
	public Date getCurrentDate() {
		Date date = getCurrentDateTime();
		return new DateTime(date).withTimeAtStartOfDay().toDate();
	}

	/**
	 * Devuelve la fecha actual.
	 * 
	 * @return
	 */
	public Date getCurrentDateTime() {
		Date now = Calendar.getInstance().getTime();
		if (offset.get() == 0L) {
			return now;
		} else {
			long modified = now.getTime() + offset.get();
			return new Date(modified);
		}
	}

	/**
	 * Permite cambiar la fecha del sistema a nivel del thread activo.
	 * 
	 * @param date
	 */
	public void setDate(Date date) {
		Date now = Calendar.getInstance().getTime();
		offset.set(date.getTime() - now.getTime());
	}

	/**
	 * Elimina la opcion de trabajar a una fecha diferente en el thread activo.
	 */
	public void resetDate() {
		offset.set(0L);
	}
}
