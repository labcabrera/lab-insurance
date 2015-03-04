package org.lab.insurance.model.jpa.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "C_CONTACT_INFORMATION")
@SuppressWarnings("serial")
public class ContactInformation implements Serializable {

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "TELEPHONE_NUMBER")
	private String telephoneNumber;

	@Column(name = "CELL_PHONE_NUMBER")
	private String cellPhoneNumber;

	@Column(name = "EMAIL")
	private String email;

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getCellPhoneNumber() {
		return cellPhoneNumber;
	}

	public void setCellPhoneNumber(String cellPhoneNumber) {
		this.cellPhoneNumber = cellPhoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
