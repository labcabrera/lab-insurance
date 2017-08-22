package org.lab.insurance.engine.processors.orders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.lab.insurance.model.Constants;
import org.lab.insurance.model.insurance.MarketOrder;
import org.lab.insurance.model.insurance.MarketOrderSource;
import org.lab.insurance.model.insurance.MarketOrderType;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.insurance.OrderDates;
import org.lab.insurance.model.insurance.OrderDistribution;
import org.lab.insurance.model.insurance.repository.MarketOrderRepository;
import org.lab.insurance.services.common.StateMachineService;
import org.lab.insurance.services.common.TimestampProvider;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MarketOrderGeneratorProcessor implements Processor {

	@Autowired
	private MarketOrderRepository marketOrderRepository;
	@Autowired
	private StateMachineService stateMachineService;
	@Autowired
	private TimestampProvider timestampProvider;

	@Override
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		log.debug("Generando market orders de {}", order);
		Mutable<BigDecimal> buyGrossAmount = new MutableObject<BigDecimal>();
		Mutable<BigDecimal> buyNetAmount = new MutableObject<BigDecimal>();
		// En primer lugar procesamos las ventas
		createSellMarketOrders(order, buyGrossAmount, buyNetAmount);
		createBuyMarketOrders(order, buyGrossAmount.getValue(), buyNetAmount.getValue());
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
		// En segundo lugar procesamos la compras
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
				marketOrder.setAsset(distribution.getAsset());
				marketOrder.setGrossAmount(grossAmount);
				marketOrder.setNetAmount(netAmount);
				marketOrder.setOrder(order);
				marketOrder.setDates(new OrderDates());
				marketOrder.getDates().setEffective(order.getDates().getEffective());
				marketOrder.getDates().setValueDate(order.getDates().getValueDate());
				marketOrder.getDates().setProcessed(timestampProvider.getCurrentDateTime());
				marketOrder.setType(MarketOrderType.BUY);
				marketOrder.setSource(MarketOrderSource.AMOUNT);
				marketOrderRepository.save(marketOrder);
				order.getMarketOrders().add(marketOrder);
				stateMachineService.createTransition(marketOrder, Constants.MarketOrderStates.PROCESSED);
			}
		}
	}
}
