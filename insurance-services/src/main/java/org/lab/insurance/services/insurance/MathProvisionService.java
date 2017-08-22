package org.lab.insurance.services.insurance;

import java.util.Date;

import javax.inject.Inject;

import org.lab.insurance.model.exceptions.NoCotizationException;
import org.lab.insurance.model.portfolio.Portfolio;
import org.lab.insurance.model.portfolio.PortfolioMathProvision;
import org.lab.insurance.services.accounting.PortfolioService;
import org.lab.insurance.services.common.TimestampProvider;
import org.springframework.transaction.annotation.Transactional;

public class MathProvisionService {

	@Inject
	private TimestampProvider timestampProvider;
	@Inject
	private PortfolioService portfolioService;
	@Inject
	private CotizationsService cotizationsService;

	public PortfolioMathProvision findAtDate(Portfolio portfolio, Date date, boolean cotizate) {
		throw new RuntimeException("Not implemented jpa -> mongo");
		// EntityManager entityManager = entityManagerProvider.get();
		// TypedQuery<PortfolioMathProvision> query =
		// entityManager.createNamedQuery("PortfolioMathProvision.selectByDate",
		// PortfolioMathProvision.class);
		// query.setParameter("portfolio", portfolio);
		// query.setParameter("date", date);
		// try {
		// return query.getSingleResult();
		// }
		// catch (NoResultException ex) {
		// return createMathProvisionAtDate(portfolio, date, cotizate);
		// }
	}

	@Transactional
	public PortfolioMathProvision createMathProvisionAtDate(Portfolio portfolio, Date date, boolean cotizate)
			throws NoCotizationException {
		throw new RuntimeException("Not implemented jpa -> mongo");
		// EntityManager entityManager = entityManagerProvider.get();
		// PortfolioMathProvision mp = new PortfolioMathProvision();
		// mp.setPortfolio(portfolio);
		// mp.setValueDate(new DateTime(date).withTimeAtStartOfDay().toDate());
		// mp.setGenerated(timestampProvider.getCurrentDateTime());
		// mp.setMathProvisions(new ArrayList<MathProvision>());
		// List<Investment> investments = portfolioService.findActiveInvestmentsAtDate(portfolio, date);
		// TypedQuery<BigDecimal> queryUnitsIn = entityManager.createNamedQuery("PortfolioOperation.unitsIn",
		// BigDecimal.class);
		// TypedQuery<BigDecimal> queryUnitsOut = entityManager.createNamedQuery("PortfolioOperation.unitsOut",
		// BigDecimal.class);
		// BigDecimal totalAmount = BigDecimal.ZERO;
		// for (Investment investment : investments) {
		// BigDecimal unitsIn = BigMath.safeNull(
		// queryUnitsIn.setParameter("investment", investment).setParameter("date", date).getSingleResult());
		// BigDecimal unitsOut = BigMath.safeNull(
		// queryUnitsOut.setParameter("investment", investment).setParameter("date", date).getSingleResult());
		// MathProvision mathProvision = new MathProvision();
		// mathProvision.setPortfolioMathProvision(mp);
		// mathProvision.setInvestment(investment);
		// mathProvision.setUnits(unitsIn.subtract(unitsOut));
		// if (BigMath.isNotZero(mathProvision.getUnits())) {
		// if (cotizate) {
		// AssetPrice assetPrice = cotizationsService.findPriceAtDate(investment.getAsset(), date);
		// BigDecimal amount = assetPrice.getBuyPrice().multiply(mathProvision.getUnits()).setScale(2,
		// RoundingMode.HALF_EVEN);
		// mathProvision.setAmount(amount);
		// }
		// else {
		// mathProvision.setAmount(BigDecimal.ZERO);
		// }
		// mathProvision.setCost(BigDecimal.ZERO);
		// mp.getMathProvisions().add(mathProvision);
		// totalAmount = totalAmount.add(mathProvision.getAmount());
		// }
		// }
		// mp.setValue(totalAmount);
		// entityManager.persist(mp);
		// return mp;
	}
}
