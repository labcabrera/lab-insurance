package org.lab.insurance.domain.hateoas.insurance;

import java.math.BigDecimal;

import org.lab.insurance.domain.core.insurance.MarketOrderType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDistributionItem {

	BigDecimal netAmount;
	BigDecimal percent;
	AssetResource asset;
	MarketOrderType type;

}
