package org.lab.insurance.model.contract;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@DiscriminatorValue("CF")
@Table(name = "CTR_FS_CONTRACT_FEES")
@SuppressWarnings("serial")
public class ContractFees extends FinancialService {

}
