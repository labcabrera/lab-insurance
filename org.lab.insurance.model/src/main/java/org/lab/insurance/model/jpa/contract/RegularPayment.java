package org.lab.insurance.model.jpa.contract;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("RP")
@Table(name = "C_REGULAR_PAYMENT")
@SuppressWarnings("serial")
public class RegularPayment extends FinancialService {

}
