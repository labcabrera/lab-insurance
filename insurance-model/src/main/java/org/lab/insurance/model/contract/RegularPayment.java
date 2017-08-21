package org.lab.insurance.model.contract;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("RP")
@Table(name = "CTR_FS_REGULAR_PAYMENT")
@SuppressWarnings("serial")
public class RegularPayment extends FinancialService {

}
