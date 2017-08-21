package org.lab.insurance.model.common.internal;

import java.util.HashMap;
import java.util.Map;

public class MessageEntry {

	private String text;
	private Map<String, String> params;

	public MessageEntry() {
	}

	public MessageEntry(String text) {
		this.text = text;
	}

	public MessageEntry(String text, Map<String, String> params) {
		this.text = text;
		this.params = params;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public MessageEntry withParameter(String key, String value) {
		synchronized (this) {
			if (params == null) {
				params = new HashMap<String, String>();
			}
		}
		params.put(key, value);
		return this;
	}

	@Override
	public String toString() {
		return text;
	}
}
