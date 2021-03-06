package org.lab.insurance.domain.core.legalentity;

import java.util.Date;
import java.util.List;

import org.lab.insurance.domain.core.geo.Address;
import org.springframework.data.annotation.Reference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Document
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
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
