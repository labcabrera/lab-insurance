package org.lab.insurance.model.jpa.contract;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("RS")
@Table(name = "CTR_FS_REGULAR_SURRENDER")
@SuppressWarnings("serial")
public class RegularSurrender extends FinancialService {

}
