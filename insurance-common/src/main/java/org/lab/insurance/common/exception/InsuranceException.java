package org.lab.insurance.common.exception;

@SuppressWarnings("serial")
public class InsuranceException extends RuntimeException {

	public InsuranceException() {
		super();
	}

	public InsuranceException(String message) {
		super(message);
	}

	public InsuranceException(String message, Throwable cause) {
		super(message, cause);
	}

	public InsuranceException(Throwable cause) {
		super(cause);
	}
}
