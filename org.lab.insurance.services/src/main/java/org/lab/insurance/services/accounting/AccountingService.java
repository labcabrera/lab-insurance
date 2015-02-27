package org.lab.insurance.services.accounting;

import java.math.BigDecimal;
import java.util.Date;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.lab.insurance.model.jpa.accounting.Investment;
import org.lab.insurance.model.jpa.accounting.PortfolioOperation;
import org.lab.insurance.model.jpa.insurance.BaseAsset;
import org.lab.insurance.model.jpa.insurance.MarketOrder;

import com.google.inject.Provider;

public class AccountingService {

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	public PortfolioOperation accountUnits(Investment from, Investment to, BaseAsset asset, BigDecimal units, Date valueDate, MarketOrder marketOrder) {
		PortfolioOperation operation = new PortfolioOperation();
		operation.setDebe(from);
		operation.setHaber(to);
		operation.setUnits(units);
		operation.setValueDate(valueDate);
		operation.setMarketOrder(marketOrder);
		EntityManager entityManager = entityManagerProvider.get();
		entityManager.persist(operation);
		return operation;
	}
}
