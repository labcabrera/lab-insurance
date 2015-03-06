package org.lab.insurance.model.jpa.contract;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("SL")
@Table(name = "CTR_FS_STOP_LOSS")
@SuppressWarnings("serial")
public class StopLoss extends FinancialService {

}
