package org.lab.insurance.model.legalentity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.lab.insurance.model.HasIdentifier;

@Entity
@Table(name = "LGE_PERSON_LEGAL_INFO")
@SuppressWarnings("serial")
public class PersonLegalInfo implements Serializable, HasIdentifier<String> {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	/**
	 * Indica las situacion de dependencia de la persona (si no es dependiente sera <code>null</code>).
	 */
	@Column(name = "DEPENDENT_SITUATION", length = 16)
	@Enumerated(EnumType.STRING)
	private DependantSituation dependentSituation;

	/**
	 * Indica la situación del menor del suscriptor (su no es un menor sera <code>null</code>).
	 */
	@Column(name = "MINOR_SITUATION", length = 16)
	@Enumerated(EnumType.STRING)
	private MinorSituation minorSituation;

	/**
	 * Indica si la situación de dependencia es permanente.
	 */
	@Column(name = "DEPENDENT_SITUATION_PERMANENT", length = 1)
	private Boolean permanentDependentSituation = Boolean.FALSE;

	/**
	 * Indica cuando finaliza la situación de dependencia.
	 */
	@Column(name = "DEPENDENT_SITUATION_END")
	@Temporal(TemporalType.DATE)
	private Date dependentSituationEnd;

	/**
	 * Profesion.
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PROFESSION_ID")
	private Profession profession;

	/**
	 * Fecha de fallecimiento.
	 */
	@Column(name = "DEATH_DATE")
	@Temporal(TemporalType.DATE)
	private Date deathDate;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public DependantSituation getDependentSituation() {
		return dependentSituation;
	}

	public void setDependentSituation(DependantSituation dependentSituation) {
		this.dependentSituation = dependentSituation;
	}

	public Date getDependentSituationEnd() {
		return dependentSituationEnd;
	}

	public void setDependentSituationEnd(Date dependentSituationEnd) {
		this.dependentSituationEnd = dependentSituationEnd;
	}

	public Boolean getPermanentDependentSituation() {
		return permanentDependentSituation;
	}

	public void setPermanentDependentSituation(Boolean permanentDependentSituation) {
		this.permanentDependentSituation = permanentDependentSituation;
	}

	public MinorSituation getMinorSituation() {
		return minorSituation;
	}

	public void setMinorSituation(MinorSituation minorSituation) {
		this.minorSituation = minorSituation;
	}

	public Date getDeathDate() {
		return deathDate;
	}

	public void setDeathDate(Date deathDate) {
		this.deathDate = deathDate;
	}

	public Profession getProfession() {
		return profession;
	}

	public void setProfession(Profession profession) {
		this.profession = profession;
	}
}
