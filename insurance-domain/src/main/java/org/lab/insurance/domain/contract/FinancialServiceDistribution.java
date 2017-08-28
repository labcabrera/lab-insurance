package org.lab.insurance.domain.contract;

import java.math.BigDecimal;

import org.lab.insurance.domain.HasAsset;
import org.lab.insurance.domain.insurance.Asset;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class FinancialServiceDistribution implements HasAsset {

	@Id
	private String id;
	private Asset asset;
	private BigDecimal activationPercent;

}
