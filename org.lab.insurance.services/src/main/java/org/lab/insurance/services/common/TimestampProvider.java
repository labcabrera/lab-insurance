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
	public Date getCurrentDate() {
		return Calendar.getInstance().getTime();
	}

}
