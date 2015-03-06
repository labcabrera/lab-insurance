package org.lab.insurance.model.jpa.legalentity;

import java.io.Serializable;
import java.util.List;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.lab.insurance.model.HasIdentifier;
import org.lab.insurance.model.HasName;
import org.lab.insurance.model.jpa.common.Account;
import org.lab.insurance.model.jpa.geo.Address;

/**
 * Clase base de entidades fisicas y juridicas.
 */
@Entity
@Table(name = "LGE_ABSTRACT_LEGAL_ENTITY")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "DTYPE")
@SuppressWarnings("serial")
public abstract class AbstractLegalEntity implements Serializable, HasIdentifier<String>, HasName {

	@Id
	@Column(name = "ID", length = 36)
	@GeneratedValue(generator = "system-uuid")
	protected String id;

	/**
	 * Informacion de identificacion legal de la entidad (DNI, CIF, Pasaporte, etc).
	 */
	@OneToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "ID_CARD_ID", nullable = false)
	protected IdCard idCard;

	/**
	 * Direccion postal de la entidad.
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "POSTAL_ADDRESS_ID", nullable = false)
	protected Address postalAddress;

	/**
	 * Direccion fiscal de la entidad.
	 */
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "FISCAL_ADDRESS_ID")
	protected Address fiscalAddress;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "CONTACT_INFORMATION_ID")
	protected ContactInformation contactInformation;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "CTR_ABSTRACT_LEGAL_ENTITY_ACCOUNT", joinColumns = { @JoinColumn(name = "LEGAL_ENTITY_ID", referencedColumnName = "ID") }, inverseJoinColumns = { @JoinColumn(name = "ACCOUNT_ID", referencedColumnName = "ID") })
	private List<Account> accounts;

	/**
	 * Nombre de la entidad.
	 */
	@Column(name = "NAME", length = 64, nullable = false)
	protected String name;

	/**
	 * Codigo interno de la entidad.
	 */
	@Column(name = "INTERNAL_CODE", length = 64)
	protected String internalCode;

	/**
	 * Codigo externo de la entidad (para aquellos casos en los que provenga de un sistema externo).
	 */
	@Column(name = "EXTERNAL_CODE", length = 64)
	protected String externalCode;

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
