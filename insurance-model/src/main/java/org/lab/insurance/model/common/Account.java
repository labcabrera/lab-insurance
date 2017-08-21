package org.lab.insurance.model.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.lab.insurance.model.HasIdentifier;

@Entity
@Table(name = "COM_ACCOUNT")
@SuppressWarnings("serial")
public class Account implements Serializable, HasIdentifier<String> {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	/**
	 * Nombre del titular de la cuenta.
	 */
	@Column(name = "ACCOUNT_HOLDER", length = 128)
	private String accountHolder;

	/**
	 * Número de cuenta.
	 */

	@Column(name = "ACCOUNT_NUMBER", length = 100)
	private String accountNumber;

	/**
	 * Tipo de cuenta.
	 */
	@Column(name = "TYPE", length = 15)
	@Enumerated(EnumType.STRING)
	private AccountType type;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public AccountType getType() {
		return type;
	}

	public void setType(AccountType type) {
		this.type = type;
	}
}
