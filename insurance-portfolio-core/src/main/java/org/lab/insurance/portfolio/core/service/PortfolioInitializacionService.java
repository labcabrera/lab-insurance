package org.lab.insurance.portfolio.core.service;

import java.util.ArrayList;
import java.util.List;

import org.lab.insurance.domain.contract.Contract;
import org.lab.insurance.domain.contract.repository.ContractRepository;
import org.lab.insurance.domain.messaging.ContractRefMessage;
import org.lab.insurance.domain.portfolio.ContractPortfolioRelation;
import org.lab.insurance.domain.portfolio.Portfolio;
import org.lab.insurance.domain.portfolio.PortfolioType;
import org.lab.insurance.domain.portfolio.repository.ContractPortfolioRelationRepository;
import org.lab.insurance.domain.portfolio.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PortfolioInitializacionService {

	@Autowired
	private ContractRepository contractRepo;
	@Autowired
	private PortfolioRepository portfolioRepo;
	@Autowired
	private ContractPortfolioRelationRepository repo;

	public ContractPortfolioRelation initialize(ContractRefMessage msg) {
		log.info("Intializing contract {} portfolios");

		Contract contract = contractRepo.findOne(msg.getContractId());
		String cn = contract.getNumber();
		// TODO validate contract status (filtering)

		List<Portfolio> portfolios = new ArrayList<>();
		portfolios.add(Portfolio.builder().type(PortfolioType.PASSIVE).name(cn + "/passive").build());
		portfolios.add(Portfolio.builder().type(PortfolioType.ACTIVE).name(cn + "/active").build());
		portfolios.add(Portfolio.builder().type(PortfolioType.FEES).name(cn + "/fees").build());
		portfolios.add(Portfolio.builder().type(PortfolioType.FISCALITY).name(cn + "/fiscality").build());

		portfolioRepo.save(portfolios);

		ContractPortfolioRelation entity = new ContractPortfolioRelation();
		entity.setContract(contract);
		entity.setPortfolios(new ArrayList<>());
		entity.getPortfolios().addAll(portfolios);

		repo.save(entity);
		return entity;
	}
}
