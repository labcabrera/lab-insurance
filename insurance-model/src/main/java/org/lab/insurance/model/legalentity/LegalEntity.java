package org.lab.insurance.model.legalentity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "LGE_LEGAL_ENTITY")
@SuppressWarnings("serial")
@DiscriminatorValue("L")
public class LegalEntity extends AbstractLegalEntity {

}
