package org.lab.insurance.model.jpa;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.lab.insurance.model.HasIdentifier;
import org.lab.insurance.model.HasName;
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
public abstract class AbstractLegalEntity implements Serializable, HasIdentifier<String>, HasName {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	protected String id;

	@Column(name = "NAME", length = 64, nullable = false)
	protected String name;

	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "ID_CARD_ID", nullable = false)
	protected IdCard idCard;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "POSTAL_ADDRESS_ID", nullable = false)
	protected Address postalAddress;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "FISCAL_ADDRESS_ID")
	protected Address fiscalAddress;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "CONTACT_INFORMATION_ID")
	protected ContactInformation contactInformation;

	@Override
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

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public Address getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	public Address getFiscalAddress() {
		return fiscalAddress;
	}

	public void setFiscalAddress(Address fiscalAddress) {
		this.fiscalAddress = fiscalAddress;
	}

	public ContactInformation getContactInformation() {
		return contactInformation;
	}

	public void setContactInformation(ContactInformation contactInformation) {
		this.contactInformation = contactInformation;
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
