package org.lab.insurance.model.legalentity;

import java.util.Date;
import java.util.List;

import org.lab.insurance.model.geo.Address;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Document
public class Person extends AbstractLegalEntity {

	@Reference
	private List<Person> representants;

	@Reference
	private Address birthAddress;

	private PersonLegalInfo legalInfo;
	private String firstSurname;
	private String secondSurname;
	private String bornSurname;
	private Date birthDate;
	private CivilStatus civilStatus;
	private Treatment treatment;

}
