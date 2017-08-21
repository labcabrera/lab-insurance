package org.lab.insurance.model.system;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.lab.insurance.model.HasActivationRange;
import org.lab.insurance.model.HasIdentifier;

/**
 * Representa un correo utilizado por el servicio de alertas.
 */
@Entity
@Table(name = "SYS_APP_MAIL_RECIPIENT")
@SuppressWarnings("serial")
@NamedQueries({ @NamedQuery(name = "AppMailRecipient.selectActives", query = "select e from AppMailRecipient e where e.endDate is null") })
public class AppMailRecipient implements Serializable, HasIdentifier<String>, HasActivationRange {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "NAME", length = 256, nullable = false)
	private String name;

	@Column(name = "MAIL_ADDRESS", length = 256, nullable = false)
	private String mailAddress;

	@Column(name = "START_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Column(name = "END_DATE", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getMailAddress() {
		return mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}