package org.lab.insurance.services.accounting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.lab.insurance.model.Constants;
import org.lab.insurance.model.jpa.accounting.Investment;
import org.lab.insurance.model.jpa.accounting.Portfolio;
import org.lab.insurance.model.jpa.accounting.PortfolioOperation;
import org.lab.insurance.model.jpa.insurance.BaseAsset;
import org.lab.insurance.model.jpa.insurance.MarketOrder;
import org.lab.insurance.model.jpa.insurance.MarketOrderType;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.services.common.StateMachineService;

import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

public class AccountingService {

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	@Inject
	private PortfolioService portfolioService;
	@Inject
	private StateMachineService stateMachineService;

	@Transactional
	public List<PortfolioOperation> account(Order order) {
		List<PortfolioOperation> list = new ArrayList<PortfolioOperation>();
		Portfolio orderActivo = order.getProcessInfo().getPortfolioActivo();
		Portfolio orderPasivo = order.getProcessInfo().getPortfolioPasivo();
		for (MarketOrder marketOrder : order.getMarketOrders()) {
			BaseAsset asset = marketOrder.getAsset();
			Date valueDate = order.getDates().getValueDate();
			BigDecimal units = marketOrder.getUnits();
			Portfolio portfolioDebe = marketOrder.getType() == MarketOrderType.BUY ? orderActivo : orderPasivo;
			Portfolio portfolioHaber = marketOrder.getType() == MarketOrderType.BUY ? orderPasivo : orderActivo;
			Investment debe = portfolioService.findOrCreateActiveInvestment(portfolioDebe, asset, valueDate);
			Investment haber = portfolioService.findOrCreateActiveInvestment(portfolioHaber, asset, valueDate);
			list.add(accountUnits(debe, haber, asset, units, valueDate, marketOrder));
		}
		stateMachineService.createTransition(order, Constants.OrderStates.ACCOUNTED);
		return list;

	}

	public PortfolioOperation accountUnits(Investment from, Investment to, BaseAsset asset, BigDecimal units, Date valueDate, MarketOrder marketOrder) {
		PortfolioOperation operation = new PortfolioOperation();
		operation.setDebe(from);
		operation.setHaber(to);
		operation.setUnits(units);
		operation.setValueDate(valueDate);
		operation.setMarketOrder(marketOrder);
		EntityManager entityManager = entityManagerProvider.get();
		entityManager.persist(operation);
		entityManager.flush();
		return operation;
	}
}
