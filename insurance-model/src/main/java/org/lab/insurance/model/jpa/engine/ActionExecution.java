package org.lab.insurance.model.jpa.engine;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.lab.insurance.model.jpa.converters.ClassConverter;

@Entity
@Table(name = "EGN_ACTION_EXECUTION")
@SuppressWarnings("serial")
public class ActionExecution implements Serializable {

	@Id
	@Column(name = "ID", length = 36)
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
	@Column(name = "ACTION_ENTITY_CLASS", nullable = false, length = 516)
	@Convert(converter = ClassConverter.class)
	private Class<?> actionEntityClass;

	@Column(name = "ACTION_ENTITY_JSON", columnDefinition = "CLOB")
	private String actionEntityJson;

	@Column(name = "RESULT_JSON", columnDefinition = "CLOB")
	private String resultJson;

	/**
	 * Indica la prioridad de la accion cuando hubiera varias acciones programadas para un mismo instante. Los valores mas bajos se ejecutan
	 * antes que los mas altos.
	 */
	@Column(name = "PRIORITY")
	private Integer priority;

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

	public Class<?> getActionEntityClass() {
		return actionEntityClass;
	}

	public void setActionEntityClass(Class<?> actionEntityClass) {
		this.actionEntityClass = actionEntityClass;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
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
