package org.lab.insurance.model.exceptions;

/**
 * Categoria de errores de configuracion del sistema (faltan parametros de configuracion o estos no son correctos).
 */
@SuppressWarnings("serial")
public class ConfigurationException extends RuntimeException {

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
}
