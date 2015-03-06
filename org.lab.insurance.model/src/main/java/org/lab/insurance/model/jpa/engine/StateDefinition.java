package org.lab.insurance.model.jpa.engine;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.lab.insurance.model.jpa.converters.ClassConverter;

@Entity
@Table(name = "EGN_STATE_DEFINITION")
@SuppressWarnings("serial")
public class StateDefinition implements Serializable {

	@Id
	@Column(name = "ID", length = 64)
	private String id;

	@Column(name = "NAME", length = 64, nullable = false)
	private String name;

	@Column(name = "ENTITY_CLASS", nullable = false, length = 516)
	@Convert(converter = ClassConverter.class)
	private Class<?> entityClass;

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

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}

	@Override
	public String toString() {
		return id != null ? id : super.toString();
	}
}
