package org.lab.insurance.domain.insurance;

import java.math.BigDecimal;

import org.lab.insurance.domain.HasAsset;
import org.lab.insurance.domain.HasState;
import org.lab.insurance.domain.engine.State;
import org.lab.insurance.domain.validation.ValidMarketOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
@ValidMarketOrder
public class MarketOrder implements HasState, HasAsset {

	@Id
	private String id;

	@DBRef
	private Asset asset;

	private MarketOrderType type;
	private MarketOrderSource source;

	private State currentState;
	private OrderDates dates;

	private BigDecimal units;
	private BigDecimal grossAmount;
	private BigDecimal netAmount;
	private BigDecimal nav;

}
