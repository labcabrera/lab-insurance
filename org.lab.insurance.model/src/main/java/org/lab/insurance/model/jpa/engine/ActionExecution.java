package org.lab.insurance.model.jpa.engine;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SYS_ACTION_EXECUTION")
@SuppressWarnings("serial")
public class ActionExecution implements Serializable {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "EXECUTED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date executed;

	@Column(name = "SCHEDULED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date scheduled;

	@Column(name = "CANCELLED")
	@Temporal(TemporalType.TIMESTAMP)
	private Date cancelled;

	@Column(name = "FAILURE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date failure;

	// TODO el columnDefinition depende del proveedor de base de datos. En algunos es de tipo TEXT, para derby es necesario el CLOB.
	@Column(name = "ACTION_ENTITY_CLASS", columnDefinition = "CLOB")
	private String actionEntityClass;

	@Column(name = "ACTION_ENTITY_JSON", columnDefinition = "CLOB")
	private String actionEntityJson;

	@Column(name = "RESULT_JSON", columnDefinition = "CLOB")
	private String resultJson;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getScheduled() {
		return scheduled;
	}

	public void setScheduled(Date scheduled) {
		this.scheduled = scheduled;
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

	public String getActionEntityClass() {
		return actionEntityClass;
	}

	public void setActionEntityClass(String actionEntityClass) {
		this.actionEntityClass = actionEntityClass;
	}

	public String getActionEntityJson() {
		return actionEntityJson;
	}

	public void setActionEntityJson(String actionEntityJson) {
		this.actionEntityJson = actionEntityJson;
	}

	public Date getFailure() {
		return failure;
	}

	public void setFailure(Date failure) {
		this.failure = failure;
	}

	public String getResultJson() {
		return resultJson;
	}

	public void setResultJson(String resultJson) {
		this.resultJson = resultJson;
	}
}
