package org.lab.insurance.portfolio.core.service;

import org.lab.insurance.domain.contract.Contract;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PortfolioInitializacionService {

	public Contract initialize(Contract contract) {
		log.info("Intializing contract {} portfolios");
		// TODO
		return contract;
	}
}
