package org.lab.insurance.model.contract;

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

import org.lab.insurance.model.HasActivationRange;
import org.lab.insurance.model.HasContract;
import org.lab.insurance.model.HasIdentifier;
import org.lab.insurance.model.common.internal.NotSerializable;
import org.lab.insurance.model.legalentity.AbstractLegalEntity;

@Entity
@Table(name = "CTR_CONTRACT_ENTITY_RELATION")
@SuppressWarnings("serial")
public class PolicyEntityRelation implements Serializable, HasIdentifier<String>, HasContract, HasActivationRange {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "TYPE", length = 16, nullable = false)
	private ContractRelationType type;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.DETACH }, optional = false)
	@JoinColumn(name = "CONTRACT_ID", nullable = false)
	@NotSerializable
	private Contract contract;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, optional = false)
	@JoinColumn(name = "LEGAL_ENTITY_ID", nullable = false)
	private AbstractLegalEntity legalEntity;

	@Column(name = "START_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	@Column(name = "PERCENT", nullable = false)
	private BigDecimal relationPercent;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public ContractRelationType getType() {
		return type;
	}

	public void setType(ContractRelationType type) {
		this.type = type;
	}

	@Override
	public Contract getContract() {
		return contract;
	}

	@Override
	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public AbstractLegalEntity getLegalEntity() {
		return legalEntity;
	}

	public void setLegalEntity(AbstractLegalEntity legalEntity) {
		this.legalEntity = legalEntity;
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
