package org.lab.insurance.contract.creation.core.service;

import org.lab.insurance.contract.creation.core.client.PortfolioInitializationClient;
import org.lab.insurance.domain.contract.Contract;
import org.lab.insurance.domain.portfolio.ContractPortfolioRelation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ContractCreatedProcessor {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private PortfolioInitializationClient portfolioInitClient;

	public Contract process(Contract contract) {
		log.debug("Processing contract {}", contract.getId());

		// portfolioInitClient.init(contract.getId());
		//
		ResponseEntity<ContractPortfolioRelation> exchange = restTemplate.exchange(
				"http://insurance-portfolio-gateway/init/" + contract.getId(), HttpMethod.POST, null,
				new ParameterizedTypeReference<ContractPortfolioRelation>() {
				});
		log.debug("Portfolio relation {}", exchange.getBody().getId());

		return contract;
	}

}
