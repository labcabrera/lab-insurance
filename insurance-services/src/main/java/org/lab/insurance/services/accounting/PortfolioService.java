package org.lab.insurance.services.accounting;

import java.util.Date;
import java.util.List;

import org.lab.insurance.model.insurance.BaseAsset;
import org.lab.insurance.model.portfolio.Investment;
import org.lab.insurance.model.portfolio.Portfolio;
import org.lab.insurance.model.portfolio.PortfolioType;
import org.lab.insurance.model.portfolio.repository.PorfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class PortfolioService {

	@Autowired
	private PorfolioRepository porfolioRepository;

	public Portfolio createPortfolio(String name, PortfolioType type) {
		Portfolio portfolio = new Portfolio();
		portfolio.setName(name);
		portfolio.setType(type);
		porfolioRepository.save(portfolio);
		return portfolio;
	}

	public Investment findOrCreateActiveInvestment(Portfolio portfolio, BaseAsset asset, Date date) {
		throw new RuntimeException("Not implemented jpa -> mongo");
		// EntityManager entityManager = entityManagerProvider.get();
		// TypedQuery<Investment> query = entityManager.createNamedQuery("Investment.selectAtDateByAsset",
		// Investment.class);
		// query.setParameter("portfolio", portfolio);
		// query.setParameter("asset", asset);
		// query.setParameter("date", date);
		// List<Investment> list = query.getResultList();
		// if (list.isEmpty()) {
		// Investment investment = new Investment();
		// investment.setPortfolio(portfolio);
		// investment.setAsset(asset);
		// investment.setStartDate(date);
		// entityManager.persist(investment);
		// entityManager.flush();
		// return investment;
		//
		// }
		// else {
		// return list.iterator().next();
		// }
	}

	public List<Investment> findActiveInvestmentsAtDate(Portfolio portfolio, Date date) {
		throw new RuntimeException("Not implemented jpa -> mongo");
		// EntityManager entityManager = entityManagerProvider.get();
		// TypedQuery<Investment> query = entityManager.createNamedQuery("Investment.selectAtDate", Investment.class);
		// query.setParameter("portfolio", portfolio);
		// query.setParameter("date", date);
		// return query.getResultList();
	}
}
