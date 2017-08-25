package org.lab.insurance.model.contract.repository;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.insurance.model.config.InsuranceModelConfig;
import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.product.Agreement;
import org.lab.insurance.model.product.AgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InsuranceModelConfig.class)
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
