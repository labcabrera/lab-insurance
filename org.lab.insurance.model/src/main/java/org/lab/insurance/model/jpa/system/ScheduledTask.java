package org.lab.insurance.model.jpa.system;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.lab.insurance.model.HasActivationRange;
import org.lab.insurance.model.HasIdentifier;
import org.lab.insurance.model.HasName;
import org.lab.insurance.model.Mergeable;

@Entity
@Table(name = "SYS_SCHEDULED_TASK")
public class ScheduledTask implements HasIdentifier<String>, HasName, Mergeable<ScheduledTask>, HasActivationRange {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "NAME", length = 32, nullable = false)
	private String name;

	@Column(name = "CLASSNAME", length = 256, nullable = false)
	private String className;

	@Column(name = "CRON_EXP", length = 32)
	private String cronExpression;

	@Column(name = "START_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "PARAM_KEY", length = 516)
	@Column(name = "PARAM_VALUE", length = 64)
	@CollectionTable(name = "SYS_SCHEDULED_TASK_PARAM", joinColumns = @JoinColumn(name = "TASK_ID"))
	private Map<String, String> params = new HashMap<String, String>();

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
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

	@Override
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Override
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
		this.startDate = t.startDate;
		this.endDate = t.endDate;
	}

}
