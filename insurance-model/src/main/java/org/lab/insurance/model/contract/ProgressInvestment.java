package org.lab.insurance.model.contract;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("PI")
@Table(name = "CTR_FS_PROGRESS_INVESTMENT")
@SuppressWarnings("serial")
public class ProgressInvestment extends FinancialService {

}
