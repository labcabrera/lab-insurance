package org.lab.insurance.model.jpa.common;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@DiscriminatorValue("P")
@Table(name = "C_PERSON")
@SuppressWarnings("serial")
public class Person extends AbstractLegalEntity {

	@Column(name = "FIRST_SURNAME", nullable = false, length = 32)
	private String firstSurname;

	@Column(name = "SECOND_SURNAME", length = 32)
	private String secondSurname;

	@Column(name = "BIRTH_DATE")
	@Temporal(TemporalType.DATE)
	private Date birthDate;

	@ManyToOne
	@JoinColumn(name = "BIRTH_ADDRESS_ID")
	private Address birthAddress;

	public String getFirstSurname() {
		return firstSurname;
	}

	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}

	public String getSecondSurname() {
		return secondSurname;
	}

	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Address getBirthAddress() {
		return birthAddress;
	}

	public void setBirthAddress(Address birthAddress) {
		this.birthAddress = birthAddress;
	}

	@Override
	public String toString() {
		return getName() + (firstSurname != null ? " " + firstSurname : "") + (secondSurname != null ? " " + secondSurname : "");
	}
}
