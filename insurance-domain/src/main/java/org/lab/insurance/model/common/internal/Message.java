package org.lab.insurance.model.common.internal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class Message<I> implements Serializable {

	public static final String SUCCESS = "200";
	public static final String GENERIC_ERROR = "500";
	public static final String REEXECUTION_ERROR = "510";

	private String code;
	private String message;
	private List<MessageEntry> errors;
	private List<MessageEntry> warnings;
	private List<MessageEntry> info;

	// TODO esto esta dando un problema con GSON y los genericos
	@NotSerializable
	private I payload;

	public Message() {
	}

	public Message(String message) {
		this.message = message;
	}

	public Message(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public Message(String code, String message, I payload) {
		this.code = code;
		this.message = message;
		this.payload = payload;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<MessageEntry> getErrors() {
		return errors;
	}

	public void setErrors(List<MessageEntry> errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public I getPayload() {
		return payload;
	}

	public void setPayload(I payload) {
		this.payload = payload;
	}

	public List<MessageEntry> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<MessageEntry> warnings) {
		this.warnings = warnings;
	}

	public List<MessageEntry> getInfo() {
		return info;
	}

	public void setInfo(List<MessageEntry> info) {
		this.info = info;
	}

	public boolean hasErrors() {
		return errors != null && !errors.isEmpty();
	}

	public boolean isValid() {
		return !hasErrors();
	}

	public Message<I> addError(String value) {
		return addError(new MessageEntry(value));
	}

	public Message<I> addError(MessageEntry messageEntry) {
		synchronized (this) {
			if (errors == null) {
				errors = new ArrayList<MessageEntry>();
			}
		}
		errors.add(messageEntry);
		return this;
	}

	public Message<I> addWarning(String value) {
		return addWarning(new MessageEntry(value));
	}

	public Message<I> addWarning(MessageEntry messageEntry) {
		synchronized (this) {
			if (warnings == null) {
				warnings = new ArrayList<MessageEntry>();
			}
		}
		warnings.add(messageEntry);
		return this;
	}

	public Message<I> addInfo(String value) {
		return addInfo(new MessageEntry(value));
	}

	public Message<I> addInfo(MessageEntry messageEntry) {
		synchronized (this) {
			if (info == null) {
				info = new ArrayList<MessageEntry>();
			}
		}
		info.add(messageEntry);
		return this;
	}

	public Message<I> withCode(String value) {
		this.code = value;
		return this;
	}

	public Message<I> withMessage(String value) {
		this.message = value;
		return this;
	}

	public Message<I> withError(String value) {
		addError(value);
		return this;
	}

	public Message<I> withPayload(I value) {
		this.payload = value;
		return this;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(code).append(":").append(message).append("]");
		if (errors != null) {
			for (MessageEntry error : errors) {
				sb.append(" [ERR:").append(error).append("]");
			}
		}
		if (warnings != null) {
			for (MessageEntry warning : warnings) {
				sb.append(" [WARN:").append(warning).append("]");
			}
		}
		return sb.toString();
	}

	public Message<I> addErrors(List<MessageEntry> errorMessages) {
		if (errorMessages != null) {
			for (MessageEntry message : errorMessages) {
				if (message != null) {
					addError(message);
				}
			}
		}
		return this;
	}

	public Message<I> addWarnings(List<MessageEntry> warningMessages) {
		if (warningMessages != null) {
			for (MessageEntry message : warningMessages) {
				if (message != null) {
					addWarning(message);
				}
			}
		}
		return this;
	}
}
