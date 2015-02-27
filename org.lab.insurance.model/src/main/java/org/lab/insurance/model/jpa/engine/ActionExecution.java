package org.lab.insurance.model.jpa.engine;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.lab.insurance.model.engine.ActionEntity;

@Entity
@Table(name = "S_ACTION_EXECUTION")
@SuppressWarnings("serial")
public class ActionExecution implements Serializable {

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "EXECUTION_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date executionDate;

	@Column(name = "EXECUTED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date executed;

	@Column(name = "CANCELLED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cancelled;

	private Class<? extends ActionEntity<?>> actionEntityClass;

	private String json;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getExecutionDate() {
		return executionDate;
	}

	public void setExecutionDate(Date executionDate) {
		this.executionDate = executionDate;
	}

	public Date getExecuted() {
		return executed;
	}

	public void setExecuted(Date executed) {
		this.executed = executed;
	}

	public Date getCancelled() {
		return cancelled;
	}

	public void setCancelled(Date cancelled) {
		this.cancelled = cancelled;
	}

	public Class<? extends ActionEntity<?>> getActionEntityClass() {
		return actionEntityClass;
	}

	public void setActionEntityClass(Class<? extends ActionEntity<?>> actionEntityClass) {
		this.actionEntityClass = actionEntityClass;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}
}
