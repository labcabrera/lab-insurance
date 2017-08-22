package org.lab.insurance.model.contract;

import java.math.BigDecimal;

import org.lab.insurance.model.HasAsset;
import org.lab.insurance.model.insurance.BaseAsset;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class FinancialServiceDistribution implements HasAsset {

	@Id
	private String id;
	private BaseAsset asset;
	private BigDecimal activationPercent;

}
