package org.lab.insurance.model.jpa.legalentity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.lab.insurance.model.jpa.geo.Address;

@Entity
@DiscriminatorValue("P")
@Table(name = "LGE_PERSON")
@SuppressWarnings("serial")
public class Person extends AbstractLegalEntity {

	/**
	 * Lista de representantes de la persona (generalmente en personas en regimen de dependencia).
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "LGE_PERSON_REPRESENTANT", joinColumns = { @JoinColumn(name = "PERSON_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "REPRESENTANT_ID", referencedColumnName = "ID") })
	private List<Person> representants;

	/**
	 * Informacion legal de la persona.
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "LEGAL_INFO_ID")
	private PersonLegalInfo legalInfo;

	/**
	 * Datos de la direccion de nacimiento de la persona.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "BIRTH_ADDRESS_ID")
	private Address birthAddress;

	/**
	 * Primer apellido.
	 */
	@Column(name = "FIRST_SURNAME", nullable = false, length = 64)
	private String firstSurname;

	/**
	 * Segundo apellido.
	 */
	@Column(name = "SECOND_SURNAME", length = 64)
	private String secondSurname;

	/**
	 * Apellido de nacimiento (para aquellos paises en los que por ejemplo puedes adoptar el apellido del marido).
	 */
	@Column(name = "BORN_SURNAME", length = 64)
	private String bornSurname;

	/**
	 * Fecha de nacimiento de la persona.
	 */
	@Column(name = "BIRTH_DATE")
	@Temporal(TemporalType.DATE)
	private Date birthDate;

	@Column(name = "CIVIL_STATUS", length = 16)
	@Enumerated(EnumType.STRING)
	private CivilStatus civilStatus;

	@Column(name = "TREATMENT")
	@Enumerated(EnumType.STRING)
	private Treatment treatment;

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

	public String getBornSurname() {
		return bornSurname;
	}

	public void setBornSurname(String bornSurname) {
		this.bornSurname = bornSurname;
	}

	public CivilStatus getCivilStatus() {
		return civilStatus;
	}

	public void setCivilStatus(CivilStatus civilStatus) {
		this.civilStatus = civilStatus;
	}

	public List<Person> getRepresentants() {
		return representants;
	}

	public void setRepresentants(List<Person> representants) {
		this.representants = representants;
	}

	public PersonLegalInfo getLegalInfo() {
		return legalInfo;
	}

	public void setLegalInfo(PersonLegalInfo legalInfo) {
		this.legalInfo = legalInfo;
	}

	public Treatment getTreatment() {
		return treatment;
	}

	public void setTreatment(Treatment treatment) {
		this.treatment = treatment;
	}

	@Override
	public String toString() {
		return getName() + (firstSurname != null ? " " + firstSurname : "") + (secondSurname != null ? " " + secondSurname : "");
	}
}
