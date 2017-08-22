package org.lab.insurance.model.trading;

import java.math.BigDecimal;

import org.lab.insurance.model.HasAsset;
import org.lab.insurance.model.HasState;
import org.lab.insurance.model.engine.State;
import org.lab.insurance.model.insurance.BaseAsset;
import org.lab.insurance.model.insurance.MarketOrderSource;

import lombok.Data;

@Data
public class TradeOrderDetail implements HasState<String>, HasAsset {

	private TradeOrder tradeOrder;
	private BaseAsset asset;
	private MarketOrderSource source;
	private BigDecimal amountSource;
	private BigDecimal unitsSource;
	private BigDecimal estimatedNav;
	private BigDecimal estimatedAmount;
	private BigDecimal estimatedUnits;
	private BigDecimal navOutcome;
	private BigDecimal amountOutcome;
	private BigDecimal unitsOutcome;
	private State currentState;

}
