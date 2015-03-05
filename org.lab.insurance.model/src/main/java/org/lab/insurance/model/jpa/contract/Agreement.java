package org.lab.insurance.model.jpa.contract;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.lab.insurance.model.HasActivationRange;
import org.lab.insurance.model.HasCode;
import org.lab.insurance.model.HasName;
import org.lab.insurance.model.common.NotSerializable;

/**
 * Representa un acuerdo marco.
 */
@Entity
@Table(name = "C_AGREEMENT")
@SuppressWarnings("serial")
@NamedQueries({ @NamedQuery(name = "Agreement.selectByCode", query = "select e from Agreement e where e.code = :code") })
public class Agreement implements Serializable, HasName, HasCode, HasActivationRange {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "SERVICE_INFO_ID", nullable = false)
	@NotSerializable
	private AgreementServiceInfo serviceInfo;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "VALIDATION_INFO_ID", nullable = false)
	@NotSerializable
	private AgreementValidationInfo validationInfo;

	@Column(name = "NAME", length = 64, nullable = false)
	private String name;

	@Column(name = "CODE", length = 16, nullable = false)
	private String code;

	@Column(name = "START_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Column(name = "END_DATE")
	@Temporal(TemporalType.DATE)
	private Date endDate;

	public String getId() {
		return id;
	}

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

	@Override
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public AgreementServiceInfo getServiceInfo() {
		return serviceInfo;
	}

	public void setServiceInfo(AgreementServiceInfo serviceInfo) {
		this.serviceInfo = serviceInfo;
	}

	public AgreementValidationInfo getValidationInfo() {
		return validationInfo;
	}

	public void setValidationInfo(AgreementValidationInfo validationInfo) {
		this.validationInfo = validationInfo;
	}
}
