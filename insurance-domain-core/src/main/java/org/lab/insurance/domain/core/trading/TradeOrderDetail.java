package org.lab.insurance.domain.core.trading;

import java.math.BigDecimal;

import org.lab.insurance.domain.core.HasAsset;
import org.lab.insurance.domain.core.HasState;
import org.lab.insurance.domain.core.engine.State;
import org.lab.insurance.domain.core.insurance.Asset;
import org.lab.insurance.domain.core.insurance.MarketOrderSource;
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
