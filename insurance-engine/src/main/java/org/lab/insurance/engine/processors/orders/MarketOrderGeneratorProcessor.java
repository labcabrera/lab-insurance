package org.lab.insurance.engine.processors.orders;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.lab.insurance.model.Constants;
import org.lab.insurance.model.jpa.insurance.MarketOrder;
import org.lab.insurance.model.jpa.insurance.MarketOrderSource;
import org.lab.insurance.model.jpa.insurance.MarketOrderType;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.model.jpa.insurance.OrderDates;
import org.lab.insurance.model.jpa.insurance.OrderDistribution;
import org.lab.insurance.services.common.StateMachineService;
import org.lab.insurance.services.common.TimestampProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MarketOrderGeneratorProcessor implements Processor {

	private static final Logger LOG = LoggerFactory.getLogger(MarketOrderGeneratorProcessor.class);

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	@Inject
	private StateMachineService stateMachineService;
	@Inject
	private TimestampProvider timestampProvider;

	@Override
	@Transactional
	public void process(Exchange exchange) throws Exception {
		Order order = exchange.getIn().getBody(Order.class);
		LOG.debug("Generando market orders de {}", order);
		EntityManager entityManager = entityManagerProvider.get();
		Mutable<BigDecimal> buyGrossAmount = new MutableObject<BigDecimal>();
		Mutable<BigDecimal> buyNetAmount = new MutableObject<BigDecimal>();
		// En primer lugar procesamos las ventas
		createSellMarketOrders(order, buyGrossAmount, buyNetAmount, entityManager);
		createBuyMarketOrders(order, buyGrossAmount.getValue(), buyNetAmount.getValue(), entityManager);
		entityManager.flush();
	}

	private void createSellMarketOrders(Order order, Mutable<BigDecimal> buyGrossAmount, Mutable<BigDecimal> buyNetAmount, EntityManager entityManager) {
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
		} else {
			buyGrossAmount.setValue(order.getGrossAmount());
			buyNetAmount.setValue(order.getNetAmount());
		}
	}

	private void createBuyMarketOrders(Order order, BigDecimal buyGrossAmount, BigDecimal buyNetAmount, EntityManager entityManager) {
		// En segundo lugar procesamos la compras
		if (order.getBuyDistribution() != null) {
			// TODO revisar las diferentes formas de calcular la distribucion de compra. Dejamos que esten espedificados ya los importes?
			BigDecimal partialGrossAmount = BigDecimal.ZERO;
			BigDecimal partialNetAmount = BigDecimal.ZERO;
			for (Iterator<OrderDistribution> iterator = order.getBuyDistribution().iterator(); iterator.hasNext();) {
				OrderDistribution distribution = iterator.next();
				BigDecimal percent = distribution.getPercent();
				BigDecimal grossAmount;
				BigDecimal netAmount;
				if (iterator.hasNext()) {
					grossAmount = buyGrossAmount.multiply(percent).divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN);
					netAmount = buyNetAmount.multiply(percent).divide(new BigDecimal("100"), 2, RoundingMode.HALF_EVEN);
					partialGrossAmount = partialGrossAmount.add(grossAmount);
					partialNetAmount = partialNetAmount.add(netAmount);
				} else {
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
				entityManager.persist(marketOrder);
				order.getMarketOrders().add(marketOrder);
				stateMachineService.createTransition(marketOrder, Constants.MarketOrderStates.PROCESSED);
			}
		}
	}
}
