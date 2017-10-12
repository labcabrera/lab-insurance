package org.lab.insurance.order.core.processor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.lab.insurance.common.services.StateMachineService;
import org.lab.insurance.common.services.TimestampProvider;
import org.lab.insurance.domain.core.insurance.MarketOrder;
import org.lab.insurance.domain.core.insurance.MarketOrderSource;
import org.lab.insurance.domain.core.insurance.MarketOrderType;
import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.domain.core.insurance.OrderDates;
import org.lab.insurance.domain.core.insurance.OrderDistribution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MarketOrderGeneratorProcessor {

	@Autowired
	private TimestampProvider timestampProvider;
	@Autowired
	private StateMachineService stateMachineService;

	public Order process(Order order) {
		log.info("Processing order {}", order);
		if (order.getMarketOrders() == null) {
			order.setMarketOrders(new ArrayList<>());
		}
		Mutable<BigDecimal> buyGrossAmount = new MutableObject<BigDecimal>();
		Mutable<BigDecimal> buyNetAmount = new MutableObject<BigDecimal>();
		createSellMarketOrders(order, buyGrossAmount, buyNetAmount);
		createBuyMarketOrders(order, buyGrossAmount.getValue(), buyNetAmount.getValue());
		return order;
	}

	private void createSellMarketOrders(Order order, Mutable<BigDecimal> buyGrossAmount,
			Mutable<BigDecimal> buyNetAmount) {
		if (order.getSellDistribution() != null && !order.getSellDistribution().isEmpty()) {
			buyGrossAmount.setValue(BigDecimal.ZERO);
			buyNetAmount.setValue(BigDecimal.ZERO);
			switch (order.getProcessInfo().getSellStrategy()) {
			case SELL_BY_AMOUNT:
				break;
			case SELL_BY_UNITS:
				break;
			case SELL_BY_MATH_PROVISION_PERCENT:
				break;
			default:
				break;
			}
		}
		else {
			buyGrossAmount.setValue(order.getGrossAmount());
			buyNetAmount.setValue(order.getNetAmount());
		}
	}

	private void createBuyMarketOrders(Order order, BigDecimal buyGrossAmount, BigDecimal buyNetAmount) {
		if (order.getBuyDistribution() != null) {
			// TODO revisar las diferentes formas de calcular la distribucion de compra. Dejamos que esten espedificados
			// ya los importes?
			BigDecimal partialGrossAmount = BigDecimal.ZERO;
			BigDecimal partialNetAmount = BigDecimal.ZERO;
			for (Iterator<OrderDistribution> iterator = order.getBuyDistribution().iterator(); iterator.hasNext();) {
				OrderDistribution distribution = iterator.next();
				BigDecimal percent = distribution.getPercent();
				BigDecimal grossAmount;
				BigDecimal netAmount;
				if (iterator.hasNext()) {
					grossAmount = buyGrossAmount.multiply(percent).divide(new BigDecimal("100"), 2,
							RoundingMode.HALF_EVEN);
					netAmount = buyNetAmount.multiply(percent).divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN);
					partialGrossAmount = partialGrossAmount.add(grossAmount);
					partialNetAmount = partialNetAmount.add(netAmount);
				}
				else {
					netAmount = buyNetAmount.subtract(partialNetAmount);
					grossAmount = buyGrossAmount.subtract(partialGrossAmount);
				}
				MarketOrder marketOrder = new MarketOrder();
				marketOrder.setDates(new OrderDates());
				marketOrder.setAsset(distribution.getAsset());
				marketOrder.setGrossAmount(grossAmount);
				marketOrder.setNetAmount(netAmount);
				marketOrder.setDates(new OrderDates());
				marketOrder.getDates().setEffective(order.getDates().getEffective());
				marketOrder.getDates().setValueDate(order.getDates().getValueDate());
				marketOrder.getDates().setProcessed(timestampProvider.getCurrentDate());
				marketOrder.setType(MarketOrderType.BUY);
				marketOrder.setSource(MarketOrderSource.AMOUNT);
				order.getMarketOrders().add(marketOrder);
				stateMachineService.createTransition(marketOrder, MarketOrder.States.PROCESSED.name(), false);
			}
		}
	}
}
