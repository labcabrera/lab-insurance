package org.lab.insurance.model.contract;

import org.lab.insurance.model.portfolio.Portfolio;

import lombok.Data;

@Data
public class ContractPorfolioInfo {

	private Portfolio portfolioPassive;
	private Portfolio portfolioActive;
	private Portfolio portfolioFees;

}
