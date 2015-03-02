package org.lab.insurance.model.jpa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.lab.insurance.model.jpa.common.Address;
import org.lab.insurance.model.jpa.common.IdCard;

/**
 * Clase base de entidades fisicas y juridicas.
 */
@Entity
@Table(name = "C_ABSTRACT_LEGAL_ENTITY")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@SuppressWarnings("serial")
public abstract class AbstractLegalEntity implements Serializable {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@Column(name = "NAME")
	private String name;

	/**
	 * NOTA: es redundante con el DTYPE pero en muchas ocasiones nos facilita la vida.
	 */
	@Column(name = "TYPE")
	@Enumerated(EnumType.STRING)
	private AbstractLegalEntityType type;

	@OneToOne
	@JoinColumn(name = "ID_CARD_ID")
	private IdCard idCard;

	@ManyToOne
	@JoinColumn(name = "POSTAL_ADDRESS_ID")
	private Address postalAddress;

	@ManyToOne
	@JoinColumn(name = "FISCAL_ADDRESS_ID")
	private Address fiscalAddress;

	@ManyToOne
	@JoinColumn(name = "CONTACT_INFORMATION_ID")
	private ContactInformation contactInformation;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IdCard getIdCard() {
		return idCard;
	}

	public void setIdCard(IdCard idCard) {
		this.idCard = idCard;
	}

	/**
	 * Realiza el casteo de la superclase a una implementacion especifica
	 * 
	 * @param entityClass
	 *            Clase a la que queremos convertir la entidad
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T as(Class<? extends AbstractLegalEntity> entityClass) {
		return (T) this;
	}

	public boolean isPerson() {
		return Person.class.isAssignableFrom(getClass());
	}

	public boolean isLegalEntity() {
		return LegalEntity.class.isAssignableFrom(getClass());
	}
}
