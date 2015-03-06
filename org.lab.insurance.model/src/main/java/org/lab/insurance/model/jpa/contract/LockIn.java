package org.lab.insurance.model.jpa.contract;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("LI")
@Table(name = "CTR_FS_LOCK_IN")
@SuppressWarnings("serial")
public class LockIn extends FinancialService {

}
