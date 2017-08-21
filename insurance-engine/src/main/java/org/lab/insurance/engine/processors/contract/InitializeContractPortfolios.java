package org.lab.insurance.engine.processors.contract;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.HasContract;
import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.contract.ContractPorfolioInfo;
import org.lab.insurance.model.portfolio.Portfolio;
import org.lab.insurance.model.portfolio.PortfolioType;
import org.lab.insurance.services.accounting.PortfolioService;

public class InitializeContractPortfolios implements Processor {

	@Inject
	private PortfolioService portfolioService;

	@Override
	public void process(Exchange exchange) throws Exception {
		Contract contract = exchange.getIn().getBody(HasContract.class).getContract();
		if (contract.getPortfolioInfo() == null) {
			contract.setPortfolioInfo(new ContractPorfolioInfo());
			String prefixName = contract.getNumber() + " ";
			Portfolio portfolioPassive = portfolioService.createPortfolio(prefixName + " passive", PortfolioType.PASSIVE);
			Portfolio portfolioActive = portfolioService.createPortfolio(prefixName + " active", PortfolioType.ACTIVE);
			Portfolio portfolioFees = portfolioService.createPortfolio(prefixName + " fees", PortfolioType.FEES);
			contract.getPortfolioInfo().setPortfolioPassive(portfolioPassive);
			contract.getPortfolioInfo().setPortfolioActive(portfolioActive);
			contract.getPortfolioInfo().setPortfolioFees(portfolioFees);
		}
	}
}
