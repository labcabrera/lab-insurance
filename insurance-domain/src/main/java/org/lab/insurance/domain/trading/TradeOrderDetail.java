package org.lab.insurance.domain.trading;

import java.math.BigDecimal;

import org.lab.insurance.domain.HasAsset;
import org.lab.insurance.domain.HasState;
import org.lab.insurance.domain.engine.State;
import org.lab.insurance.domain.insurance.Asset;
import org.lab.insurance.domain.insurance.MarketOrderSource;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class TradeOrderDetail implements HasState, HasAsset {

	@Id
	private String id;
	private TradeOrder tradeOrder;
	private Asset asset;
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
