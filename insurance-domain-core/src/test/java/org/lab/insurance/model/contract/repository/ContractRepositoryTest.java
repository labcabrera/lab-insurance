package org.lab.insurance.model.contract.repository;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.domain.core.contract.repository.ContractRepository;
import org.lab.insurance.domain.core.product.Agreement;
import org.lab.insurance.domain.core.product.repository.AgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@EnableMongoRepositories("org.lab.insurance.domain")
public class ContractRepositoryTest {

	@Autowired
	private AgreementRepository agreementRepository;
	@Autowired
	private ContractRepository contractRepository;

	@Test
	public void test() {
		String agreementCode = "AGREEMENT01";

		Agreement agreement = agreementRepository.findByCode(agreementCode);

		String contractNumber = UUID.randomUUID().toString();

		Contract contract = new Contract();
		contract.setAgreement(agreement);
		contract.setNumber(contractNumber);

		Contract saved = contractRepository.save(contract);

		System.out.println(saved);
	}

}
