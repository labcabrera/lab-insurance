package org.lab.insurance.portfolio.core.processor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.lab.insurance.common.exception.InsuranceException;
import org.lab.insurance.common.services.StateMachineService;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.insurance.Asset;
import org.lab.insurance.domain.core.insurance.MarketOrder;
import org.lab.insurance.domain.core.insurance.MarketOrderType;
import org.lab.insurance.domain.core.insurance.Order;
import org.lab.insurance.domain.core.portfolio.ContractPortfolioRelation;
import org.lab.insurance.domain.core.portfolio.Investment;
import org.lab.insurance.domain.core.portfolio.Portfolio;
import org.lab.insurance.domain.core.portfolio.PortfolioOperation;
import org.lab.insurance.domain.core.portfolio.PortfolioType;
import org.lab.insurance.domain.core.portfolio.repository.ContractPortfolioRelationRepository;
import org.lab.insurance.portfolio.core.service.PorfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class PortfolioOrderProcessor {

	// @Autowired
	// private PortfolioOperationRepository portfolioOperationRepository;

	@Autowired
	private PorfolioService portfolioService;

	@Autowired
	private StateMachineService stateMachineService;

	@Autowired
	private ContractPortfolioRelationRepository contractPortfolioRelationRepository;

	public Order process(Order order) {
		try {
			log.info("Accounting order {}", order);
			List<PortfolioOperation> operations = account(order);
			log.debug("Created {} portfolio operations", operations.size());

			// TODO pediente de definir el modelo de datos. Ahora esta inconsistente
			// portfolioOperationRepository.saveAll(operations);
			return order;
		}
		catch (RuntimeException ex) {
			log.error("Accounting error", ex);
			throw ex;
		}
	}

	public List<PortfolioOperation> account(Order order) {
		List<PortfolioOperation> list = new ArrayList<PortfolioOperation>();

		Contract contract = order.getContract();
		Portfolio orderPassive = null;
		Portfolio orderActive = null;

		if (order.getProcessInfo() != null) {
			orderPassive = order.getProcessInfo().getPortfolioPassive();
			orderActive = order.getProcessInfo().getPortfolioActive();
		}

		if (orderPassive == null || orderActive == null) {
			ContractPortfolioRelation relations = contractPortfolioRelationRepository.findByContract(contract);
			if (orderPassive == null) {
				Optional<Portfolio> optional = relations.getPortfolios().stream()
						.filter(x -> PortfolioType.PASSIVE == x.getType()).findFirst();
				orderPassive = optional.orElseThrow(() -> new InsuranceException("Missing passive portfolio"));
			}
			if (orderActive == null) {
				Optional<Portfolio> optional = relations.getPortfolios().stream()
						.filter(x -> PortfolioType.ACTIVE == x.getType()).findFirst();
				orderActive = optional.orElseThrow(() -> new InsuranceException("Missing active portfolio"));
			}
		}

		for (MarketOrder marketOrder : order.getMarketOrders()) {
			Asset asset = marketOrder.getAsset();
			Date valueDate = order.getDates().getValueDate();
			BigDecimal units = marketOrder.getUnits();
			Portfolio portfolioDebe = marketOrder.getType() == MarketOrderType.BUY ? orderActive : orderPassive;
			Portfolio portfolioHaber = marketOrder.getType() == MarketOrderType.BUY ? orderPassive : orderActive;
			Investment debe = portfolioService.findOrCreateActiveInvestment(portfolioDebe, asset, valueDate);
			Investment haber = portfolioService.findOrCreateActiveInvestment(portfolioHaber, asset, valueDate);
			list.add(accountUnits(debe, haber, asset, units, valueDate, marketOrder));
		}
		stateMachineService.createTransition(order, Order.States.ACCOUNTED.name(), false);
		return list;

	}

	public PortfolioOperation accountUnits(Investment from, Investment to, Asset asset, BigDecimal units,
			Date valueDate, MarketOrder marketOrder) {
		PortfolioOperation operation = new PortfolioOperation();
		operation.setSource(from);
		operation.setTarget(to);
		operation.setAsset(asset);
		operation.setUnits(units);
		operation.setAmount(BigDecimal.ZERO);
		operation.setValueDate(valueDate);
		operation.setMarketOrder(marketOrder);
		return operation;
	}

}
