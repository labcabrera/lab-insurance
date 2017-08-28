package org.lab.insurance.domain.contract;

import java.util.Date;

import org.bson.types.ObjectId;
import org.lab.insurance.domain.legalentity.LegalEntity;
import org.lab.insurance.domain.system.AppFile;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class ContractLetter {

	@Id
	private ObjectId id;

	private Contract contract;
	private AppFile appFile;
	private LegalEntity letterTarget;
	private Date generated;

}
