package org.lab.insurance.model.exceptions;

import java.util.Date;

import org.lab.insurance.model.engine.ActionEntity;

@SuppressWarnings("serial")
public class CancelAndRexecuteException extends RuntimeException {

	private final ActionEntity<?> actionEntity;
	private final Date scheduled;

	public CancelAndRexecuteException(ActionEntity<?> actionEntity, Date scheduled, String message, Throwable cause) {
		super(message, cause);
		this.actionEntity = actionEntity;
		this.scheduled = scheduled;
	}

	public CancelAndRexecuteException(ActionEntity<?> actionEntity, Date scheduled, String message) {
		this(actionEntity, scheduled, message, null);
	}

	public CancelAndRexecuteException(ActionEntity<?> actionEntity, Date scheduled) {
		this(actionEntity, scheduled, null, null);
	}

	public ActionEntity<?> getActionEntity() {
		return actionEntity;
	}

	public Date getScheduled() {
		return scheduled;
	}
}
