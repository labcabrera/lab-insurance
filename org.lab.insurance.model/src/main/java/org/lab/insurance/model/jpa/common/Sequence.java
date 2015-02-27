package org.lab.insurance.model.jpa.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "C_SEQUENCE")
@SuppressWarnings("serial")
public class Sequence implements Serializable {

	@Id
	@Column(name = "ID")
	private String id;

	@Column(name = "VALUE", nullable = false)
	private Long value;

	public Sequence() {
	}

	public Sequence(String id, Long value) {
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
}
