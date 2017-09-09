package org.lab.insurance.domain.core.insurance;

import java.math.BigDecimal;

import org.lab.insurance.domain.core.HasAsset;
import org.lab.insurance.domain.core.HasState;
import org.lab.insurance.domain.core.engine.State;
import org.lab.insurance.domain.core.validation.ValidMarketOrder;
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

	public enum States {
		INITIAL, PROCESSED, VALUED, ACCOUNTED;
	}
}
