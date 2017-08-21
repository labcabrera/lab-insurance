package org.lab.insurance.services.accounting;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.lab.insurance.model.jpa.insurance.BaseAsset;
import org.lab.insurance.model.jpa.portfolio.Investment;
import org.lab.insurance.model.jpa.portfolio.Portfolio;
import org.lab.insurance.model.jpa.portfolio.PortfolioType;

public class PortfolioService {

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	@Transactional
	public Portfolio createPortfolio(String name, PortfolioType type) {
		Portfolio portfolio = new Portfolio();
		portfolio.setName(name);
		portfolio.setType(type);
		EntityManager entityManager = entityManagerProvider.get();
		entityManager.persist(portfolio);
		entityManager.flush();
		return portfolio;
	}

	public Investment findOrCreateActiveInvestment(Portfolio portfolio, BaseAsset asset, Date date) {
		EntityManager entityManager = entityManagerProvider.get();
		TypedQuery<Investment> query = entityManager.createNamedQuery("Investment.selectAtDateByAsset", Investment.class);
		query.setParameter("portfolio", portfolio);
		query.setParameter("asset", asset);
		query.setParameter("date", date);
		List<Investment> list = query.getResultList();
		if (list.isEmpty()) {
			Investment investment = new Investment();
			investment.setPortfolio(portfolio);
			investment.setAsset(asset);
			investment.setStartDate(date);
			entityManager.persist(investment);
			entityManager.flush();
			return investment;

		} else {
			return list.iterator().next();
		}
	}

	public List<Investment> findActiveInvestmentsAtDate(Portfolio portfolio, Date date) {
		EntityManager entityManager = entityManagerProvider.get();
		TypedQuery<Investment> query = entityManager.createNamedQuery("Investment.selectAtDate", Investment.class);
		query.setParameter("portfolio", portfolio);
		query.setParameter("date", date);
		return query.getResultList();
	}
}
