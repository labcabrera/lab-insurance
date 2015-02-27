package org.lab.insurance.model.jpa.common;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;

import org.lab.insurance.model.Mergeable;

@Entity
@Table(name = "C_SCHEDULED_TASK")
public class ScheduledTask implements Mergeable<ScheduledTask> {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME", length = 32, nullable = false)
	private String name;

	@Column(name = "CLASSNAME", length = 256, nullable = false)
	private String className;

	@Column(name = "CRON_EXP", length = 32)
	private String cronExpression;

	@Column(name = "DISABLED")
	private Boolean disabled;

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "PARAM_KEY", length = 516)
	@Column(name = "PARAM_VALUE", length = 64)
	@CollectionTable(name = "m_scheduled_task_param", joinColumns = @JoinColumn(name = "TASK_ID"))
	private Map<String, String> params = new HashMap<String, String>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ScheduledTask [id=").append(id).append(", name=").append(name);
		builder.append(", className=").append(className);
		builder.append(", cronExpression=").append(cronExpression).append("]");
		return builder.toString();
	}

	@Override
	public void merge(ScheduledTask t) {
		this.name = t.name;
		this.cronExpression = t.cronExpression;
		this.className = t.className;
		this.disabled = t.disabled;
	}

}
