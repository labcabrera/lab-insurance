package org.lab.insurance.model.jpa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "C_POLICY_ENTITY_RELATION")
@SuppressWarnings("serial")
public class PolicyEntityRelation implements Serializable {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "TYPE")
	private PolicyRelationType type;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH }, optional = false)
	@JoinColumn(name = "POLICY_ID", nullable = false)
	private Policy policy;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, optional = false)
	@JoinColumn(name = "LEGAL_ENTITY_ID")
	private AbstractLegalEntity legalEntity;

	@Column(name = "START_DATE")
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Column(name = "PERCENT")
	private BigDecimal relationPercent;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PolicyRelationType getType() {
		return type;
	}

	public void setType(PolicyRelationType type) {
		this.type = type;
	}

	public Policy getPolicy() {
		return policy;
	}

	public void setPolicy(Policy policy) {
		this.policy = policy;
	}

	public AbstractLegalEntity getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(AbstractLegalEntity legalEntity) {
		this.legalEntity = legalEntity;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getRelationPercent() {
		return relationPercent;
	}

	public void setRelationPercent(BigDecimal relationPercent) {
		this.relationPercent = relationPercent;
	}

	@Override
	public String toString() {
		return PolicyEntityRelation.class.getSimpleName() + "/" + type + "/" + (legalEntity != null ? legalEntity.toString() : "<null>");
	}
}
