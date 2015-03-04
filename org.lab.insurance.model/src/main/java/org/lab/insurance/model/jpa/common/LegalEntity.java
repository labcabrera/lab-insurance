package org.lab.insurance.model.jpa.common;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "C_LEGAL_ENTITY")
@SuppressWarnings("serial")
@DiscriminatorValue("L")
public class LegalEntity extends AbstractLegalEntity {

}
