package org.lab.insurance.portfolio.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.contract.repository.ContractRepository;
import org.lab.insurance.portfolio.core.processor.PortfolioInitializacionProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EnableMongoRepositories({ "org.lab.insurance.domain", "org.lab.insurance.portfolio.core.domain" })
@EnableAutoConfiguration
@ComponentScan({ "org.lab.insurance.portfolio.core.service", "org.lab.insurance.portfolio.core.processor" })
public class PortfolioInitializacionServiceTest {

	@Autowired
	private ContractRepository contractRepo;
	@Autowired
	private PortfolioInitializacionProcessor service;

	@Test
	public void test() {
		Contract contract = new Contract();
		contract.setNumber("12345");

		contractRepo.save(contract);

		Contract request = new Contract();
		request.setId(contract.getId());

		service.initialize(request);

	}

}
