package org.lab.insurance.model.insurance;

import java.math.BigDecimal;

import org.bson.types.ObjectId;
import org.lab.insurance.model.HasAsset;
import org.lab.insurance.model.HasOrder;
import org.lab.insurance.model.HasState;
import org.lab.insurance.model.engine.State;
import org.lab.insurance.model.validation.ValidMarketOrder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
@ValidMarketOrder
public class MarketOrder implements HasOrder, HasState<String>, HasAsset {

	@Id
	private ObjectId id;
	private Order order;
	private BaseAsset asset;
	private MarketOrderType type;
	private MarketOrderSource source;
	private State currentState;
	private OrderDates dates;
	private BigDecimal units;
	private BigDecimal grossAmount;
	private BigDecimal netAmount;
	private BigDecimal nav;

}
