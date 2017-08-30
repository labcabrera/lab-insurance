package org.lab.insurance.contract.creation.core.client;

import org.lab.insurance.domain.portfolio.ContractPortfolioRelation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("insurance-portfolio-gateway")
public interface PortfolioInitializationClient {

	@RequestMapping(method = RequestMethod.POST, value = "/init/{contractId}")
	ContractPortfolioRelation init(@PathVariable("contractId") String contractId);

}
