package org.lab.insurance.order.core.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang3.Validate;
import org.lab.insurance.common.services.TimestampProvider;
import org.lab.insurance.domain.core.exceptions.NoCotizationException;
import org.lab.insurance.domain.core.insurance.AssetPrice;
import org.lab.insurance.domain.core.insurance.MarketOrder;
import org.lab.insurance.domain.core.insurance.MarketOrderSource;
import org.lab.insurance.domain.core.insurance.MarketOrderType;
import org.lab.insurance.domain.core.insurance.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class OrderValorizationProcessor {

	@Autowired
	private TimestampProvider timeStampProvider;

	public Order process(Order order) {
		log.info("Valuing order {}", order);
		Validate.isTrue(Order.States.VALUING.name().equals(order.getCurrentState().getCode()));
		order.getMarketOrders().stream().filter(x -> x.getType() == MarketOrderType.SELL).forEach(mo -> valorizate(mo));
		order.getMarketOrders().stream().filter(x -> x.getType() == MarketOrderType.BUY).forEach(mo -> valorizate(mo));
		order.getDates().setValued(timeStampProvider.getCurrentDate());
		return order;
	}

	private void valorizate(MarketOrder marketOrder) throws NoCotizationException {
		// TODO read cotization
		AssetPrice price = new AssetPrice();
		price.setPrice(BigDecimal.ONE);

		marketOrder.setNav(price.getPrice());
		if (marketOrder.getSource() == MarketOrderSource.UNITS) {
			Validate.notNull(marketOrder.getUnits());
			BigDecimal units = marketOrder.getUnits();
			BigDecimal amount = price.getPrice().multiply(units);
			marketOrder.setNetAmount(amount);
		}
		else {
			Validate.notNull(marketOrder.getNetAmount());
			BigDecimal amount = marketOrder.getNetAmount();
			Integer decimals = marketOrder.getAsset().getDecimals() != null ? marketOrder.getAsset().getDecimals() : 5;
			BigDecimal units = amount.divide(price.getPrice(), decimals, RoundingMode.HALF_EVEN);
			marketOrder.setUnits(units);
		}
	}

}
