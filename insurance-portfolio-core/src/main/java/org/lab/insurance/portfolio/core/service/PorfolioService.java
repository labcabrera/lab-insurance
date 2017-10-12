package org.lab.insurance.portfolio.core.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.lab.insurance.common.exception.InsuranceException;
import org.lab.insurance.domain.core.insurance.Asset;
import org.lab.insurance.domain.core.portfolio.Investment;
import org.lab.insurance.domain.core.portfolio.Portfolio;
import org.springframework.stereotype.Service;

@Service
public class PorfolioService {

	/**
	 * Busca o crea el Investment de un asset asociado a un portfolio. Como el investment tiene fechas de inicio/fin
	 * comprueba que el Investment este en el rango valido a partir de la fecha de entrada.
	 * 
	 * @param portfolio
	 * @param asset
	 * @param date
	 * @return
	 */
	public Investment findOrCreateActiveInvestment(Portfolio portfolio, Asset asset, Date date) {
		//@formatter:off
		List<Investment> list = portfolio.getInvestments().stream().filter(x ->
				x.getAsset().getId().equals(asset.getId()) &&
				x.getStartDate().compareTo(date) >= 0 &&
				(x.getEndDate() == null || x.getEndDate().compareTo(date) <= 0))
				.collect(Collectors.toList());
		//@formatter:on

		Investment result;

		switch (list.size()) {
		case 0:
			result = new Investment();
			result.setAsset(asset);
			result.setStartDate(date);
			portfolio.getInvestments().add(result);
			break;
		case 1:
			result = list.iterator().next();
			break;
		default:
			// TODO add extra info
			throw new InsuranceException("Several active investments have been found in the portfolio");
		}
		return result;
	}

	// public List<Investment> findActiveInvestmentsAtDate(Portfolio portfolio, Date date) {
	// EntityManager entityManager = entityManagerProvider.get();
	// TypedQuery<Investment> query = entityManager.createNamedQuery("Investment.selectAtDate", Investment.class);
	// query.setParameter("portfolio", portfolio);
	// query.setParameter("date", date);
	// return query.getResultList();
	// }

}
