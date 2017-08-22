package org.lab.insurance.services.accounting;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.lab.insurance.model.Constants;
import org.lab.insurance.model.insurance.BaseAsset;
import org.lab.insurance.model.insurance.MarketOrder;
import org.lab.insurance.model.insurance.MarketOrderType;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.portfolio.Investment;
import org.lab.insurance.model.portfolio.Portfolio;
import org.lab.insurance.model.portfolio.PortfolioOperation;
import org.lab.insurance.model.portfolio.repository.PortfolioOperationRepository;
import org.lab.insurance.services.common.StateMachineService;
import org.springframework.transaction.annotation.Transactional;

public class AccountingService {

	@Inject
	private PortfolioOperationRepository portfolioOperationRepository;
	@Inject
	private PortfolioService portfolioService;
	@Inject
	private StateMachineService stateMachineService;

	@Transactional
	public List<PortfolioOperation> account(Order order) {
		List<PortfolioOperation> list = new ArrayList<PortfolioOperation>();
		Portfolio orderPassive = order.getProcessInfo().getPortfolioPassive();
		Portfolio orderActive = order.getProcessInfo().getPortfolioActive();
		if (orderPassive == null) {
			orderPassive = order.getContract().getPortfolioInfo().getPortfolioPassive();
		}
		if (orderActive == null) {
			orderActive = order.getContract().getPortfolioInfo().getPortfolioActive();
		}
		for (MarketOrder marketOrder : order.getMarketOrders()) {
			BaseAsset asset = marketOrder.getAsset();
			Date valueDate = order.getDates().getValueDate();
			BigDecimal units = marketOrder.getUnits();
			Portfolio portfolioDebe = marketOrder.getType() == MarketOrderType.BUY ? orderActive : orderPassive;
			Portfolio portfolioHaber = marketOrder.getType() == MarketOrderType.BUY ? orderPassive : orderActive;
			Investment debe = portfolioService.findOrCreateActiveInvestment(portfolioDebe, asset, valueDate);
			Investment haber = portfolioService.findOrCreateActiveInvestment(portfolioHaber, asset, valueDate);
			list.add(accountUnits(debe, haber, asset, units, valueDate, marketOrder));
		}
		stateMachineService.createTransition(order, Constants.OrderStates.ACCOUNTED);
		return list;

	}

	public PortfolioOperation accountUnits(Investment from, Investment to, BaseAsset asset, BigDecimal units,
			Date valueDate, MarketOrder marketOrder) {
		PortfolioOperation operation = new PortfolioOperation();
		operation.setDebe(from);
		operation.setHaber(to);
		operation.setAsset(asset);
		operation.setUnits(units);
		operation.setAmount(BigDecimal.ZERO);
		operation.setValueDate(valueDate);
		operation.setMarketOrder(marketOrder);
		portfolioOperationRepository.save(operation);
		return operation;
	}
}
