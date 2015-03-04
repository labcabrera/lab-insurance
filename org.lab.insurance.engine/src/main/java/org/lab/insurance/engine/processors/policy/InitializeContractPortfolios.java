package org.lab.insurance.engine.processors.policy;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.HasContract;
import org.lab.insurance.model.jpa.Contract;
import org.lab.insurance.model.jpa.PolicyPorfolioInfo;
import org.lab.insurance.model.jpa.accounting.Portfolio;
import org.lab.insurance.model.jpa.accounting.PortfolioType;
import org.lab.insurance.services.accounting.PortfolioService;

public class InitializeContractPortfolios implements Processor {

	@Inject
	private PortfolioService portfolioService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Contract contract = exchange.getIn().getBody(HasContract.class).getContract();
		if (contract.getPortfolioInfo() == null) {
			contract.setPortfolioInfo(new PolicyPorfolioInfo());
			String prefixName = contract.getNumber() + " ";
			Portfolio portfolioPasivo = portfolioService.createPortfolio(prefixName + " pasivo", PortfolioType.PASIVO);
			Portfolio portfolioActivo = portfolioService.createPortfolio(prefixName + " activo", PortfolioType.ACTIVO);
			contract.getPortfolioInfo().setPortfolioPasivo(portfolioPasivo);
			contract.getPortfolioInfo().setPortfolioActivo(portfolioActivo);
		}
	}
}
