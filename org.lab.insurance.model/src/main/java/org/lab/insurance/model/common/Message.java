package org.lab.insurance.model.common;

public class Message<T> {

	public static final String SUCCESS = "200";
	public static final String GENERIC_ERROR = "400";

	private String message;
	private String code;
	private T payload;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public Message<T> withCode(String code) {
		this.code = code;
		return this;
	}

	public Message<T> withPayload(T payload) {
		this.payload = payload;
		return this;
	}
}
