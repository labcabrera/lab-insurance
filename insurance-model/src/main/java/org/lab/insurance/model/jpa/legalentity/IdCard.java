package org.lab.insurance.model.jpa.legalentity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LGE_IDCARD")
@SuppressWarnings("serial")
public class IdCard implements Serializable {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "NUMBER", length = 36, nullable = false)
	private String number;

	@Column(name = "TYPE")
	@Enumerated(EnumType.STRING)
	private IdCardType type;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public IdCardType getType() {
		return type;
	}

	public void setType(IdCardType type) {
		this.type = type;
	}

}
