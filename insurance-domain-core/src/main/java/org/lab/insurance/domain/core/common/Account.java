package org.lab.insurance.domain.core.common;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document
public class Account {

	public enum AccountType {

		RIB, IBAN, CCC_ES, IBAN_FR, IBAN_IT;
	}

	@Id
	private String id;

	private String accountHolder;

	private String accountNumber;

	private AccountType type;

}
