package org.lab.insurance.model.jpa.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "C_APP_MAIL_RECIPIENT")
@SuppressWarnings("serial")
@NamedQueries({ @NamedQuery(name = "AppMailRecipient.selectActives", query = "select e from AppMailRecipient e where e.disabled = false") })
public class AppMailRecipient implements Serializable {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "NAME", length = 256, nullable = false)
	private String name;

	@Column(name = "MAIL_ADDRESS", length = 256, nullable = false)
	private String mailAddress;

	@Column(name = "DISABLED", nullable = false)
	private Boolean disabled;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
}