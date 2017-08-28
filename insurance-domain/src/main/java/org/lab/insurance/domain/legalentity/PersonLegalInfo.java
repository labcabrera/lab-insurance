package org.lab.insurance.domain.legalentity;

import java.util.Date;

import lombok.Data;

@Data
public class PersonLegalInfo {

	private DependantSituation dependentSituation;
	private MinorSituation minorSituation;
	private Boolean permanentDependentSituation;
	private Date dependentSituationEnd;
	private Profession profession;
	private Date deathDate;

}
