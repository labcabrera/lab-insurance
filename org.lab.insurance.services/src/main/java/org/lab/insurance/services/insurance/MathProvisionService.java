package org.lab.insurance.services.insurance;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.lab.insurance.core.math.BigMath;
import org.lab.insurance.model.exceptions.NoCotizationException;
import org.lab.insurance.model.jpa.accounting.Investment;
import org.lab.insurance.model.jpa.accounting.MathProvision;
import org.lab.insurance.model.jpa.accounting.Portfolio;
import org.lab.insurance.model.jpa.accounting.PortfolioMathProvision;
import org.lab.insurance.model.jpa.insurance.AssetPrice;
import org.lab.insurance.services.accounting.PortfolioService;
import org.lab.insurance.services.common.TimestampProvider;

import com.google.inject.persist.Transactional;

public class MathProvisionService {

	@Inject
	private Provider<EntityManager> entityManagerProvider;
	@Inject
	private TimestampProvider timestampProvider;
	@Inject
	private PortfolioService portfolioService;
	@Inject
	private CotizationsService cotizationsService;

	public PortfolioMathProvision findAtDate(Portfolio portfolio, Date date, boolean cotizate) {
		EntityManager entityManager = entityManagerProvider.get();
		TypedQuery<PortfolioMathProvision> query = entityManager.createNamedQuery("PortfolioMathProvision.selectByDate", PortfolioMathProvision.class);
		query.setParameter("portfolio", portfolio);
		query.setParameter("date", date);
		try {
			return query.getSingleResult();
		} catch (NoResultException ex) {
			return createMathProvisionAtDate(portfolio, date, cotizate);
		}
	}

	@Transactional
	public PortfolioMathProvision createMathProvisionAtDate(Portfolio portfolio, Date date, boolean cotizate) throws NoCotizationException {
		EntityManager entityManager = entityManagerProvider.get();
		PortfolioMathProvision mp = new PortfolioMathProvision();
		mp.setPortfolio(portfolio);
		mp.setValueDate(new DateTime(date).withTimeAtStartOfDay().toDate());
		mp.setGenerated(timestampProvider.getCurrentDateTime());
		mp.setMathProvisions(new ArrayList<MathProvision>());
		List<Investment> investments = portfolioService.findActiveInvestmentsAtDate(portfolio, date);
		TypedQuery<BigDecimal> queryUnitsIn = entityManager.createNamedQuery("PortfolioOperation.unitsIn", BigDecimal.class);
		TypedQuery<BigDecimal> queryUnitsOut = entityManager.createNamedQuery("PortfolioOperation.unitsOut", BigDecimal.class);
		BigDecimal totalAmount = BigDecimal.ZERO;
		for (Investment investment : investments) {
			BigDecimal unitsIn = BigMath.safeNull(queryUnitsIn.setParameter("investment", investment).setParameter("date", date).getSingleResult());
			BigDecimal unitsOut = BigMath.safeNull(queryUnitsOut.setParameter("investment", investment).setParameter("date", date).getSingleResult());
			MathProvision mathProvision = new MathProvision();
			mathProvision.setPortfolioMathProvision(mp);
			mathProvision.setInvestment(investment);
			mathProvision.setUnits(unitsIn.subtract(unitsOut));
			if (BigMath.isNotZero(mathProvision.getUnits())) {
				if (cotizate) {
					AssetPrice assetPrice = cotizationsService.findPriceAtDate(investment.getAsset(), date);
					BigDecimal amount = assetPrice.getBuyPriceInEuros().multiply(mathProvision.getUnits()).setScale(2, RoundingMode.HALF_EVEN);
					mathProvision.setAmount(amount);
				} else {
					mathProvision.setAmount(BigDecimal.ZERO);
				}
				mathProvision.setCost(BigDecimal.ZERO);
				mp.getMathProvisions().add(mathProvision);
				totalAmount = totalAmount.add(mathProvision.getAmount());
			}
		}
		mp.setValue(totalAmount);
		entityManager.persist(mp);
		return mp;
	}
}
