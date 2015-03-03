package org.lab.insurance.engine.processors.policy;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.HasPolicy;
import org.lab.insurance.model.jpa.Policy;
import org.lab.insurance.model.jpa.PolicyPorfolioInfo;
import org.lab.insurance.model.jpa.accounting.Portfolio;
import org.lab.insurance.model.jpa.accounting.PortfolioType;
import org.lab.insurance.services.accounting.PortfolioService;

public class InitializePolicyPortfolios implements Processor {

	@Inject
	private PortfolioService portfolioService;

	@Override
	public void process(Exchange exchange) throws Exception {
		HasPolicy hasPolicy = exchange.getIn().getBody(HasPolicy.class);
		Policy policy = hasPolicy.getPolicy();
		if (policy.getPortfolioInfo() == null) {
			policy.setPortfolioInfo(new PolicyPorfolioInfo());
			String prefixName = policy.getNumber() + " ";
			Portfolio portfolioPasivo = portfolioService.createPortfolio(prefixName + " pasivo", PortfolioType.PASIVO);
			Portfolio portfolioActivo = portfolioService.createPortfolio(prefixName + " activo", PortfolioType.ACTIVO);
			policy.getPortfolioInfo().setPortfolioPasivo(portfolioPasivo);
			policy.getPortfolioInfo().setPortfolioActivo(portfolioActivo);
		}
	}
}
