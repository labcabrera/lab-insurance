package org.lab.insurance.model.jpa.insurance;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INS_CURRENCY")
@SuppressWarnings("serial")
public class Currency implements Serializable {

	@Id
	@Column(name = "ID", length = 3)
	private String id;

	@Column(name = "NAME", length = 32, nullable = false)
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
