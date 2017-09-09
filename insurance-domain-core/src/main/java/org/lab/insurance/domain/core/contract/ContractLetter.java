package org.lab.insurance.domain.core.contract;

import java.util.Date;

import org.lab.insurance.domain.core.legalentity.LegalEntity;
import org.lab.insurance.domain.core.system.AppFile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class ContractLetter {

	@Id
	private String id;

	@DBRef
	private Contract contract;

	private AppFile appFile;
	private LegalEntity letterTarget;
	private Date generated;

}
