package org.lab.insurance.model.exceptions;

@SuppressWarnings("serial")
public class ConfigurationException extends RuntimeException {

	public ConfigurationException(String message) {
		super(message);
	}

	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}
}
