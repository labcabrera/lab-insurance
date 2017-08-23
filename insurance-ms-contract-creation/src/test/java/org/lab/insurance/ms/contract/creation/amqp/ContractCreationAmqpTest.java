package org.lab.insurance.ms.contract.creation.amqp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.insurance.model.product.Agreement;
import org.lab.insurance.model.product.AgreementRepository;
import org.lab.insurance.ms.contract.creation.config.ContractCreationConfig;
import org.lab.insurance.ms.contract.creation.domain.ContractCreateInfo;
import org.lab.insurance.ms.core.InsuranceCoreConstants.Queues;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ContractCreationConfig.class })
public class ContractCreationAmqpTest {

	@Autowired
	private AgreementRepository agreementRepository;
	@Autowired
	private RabbitTemplate template;

	@Test
	public void test_load_configuration() {
		Agreement agreement = agreementRepository.findByCode("AM01");

		ContractCreateInfo info = new ContractCreateInfo();
		info.setAgreement(agreement);

		Object response = template.convertSendAndReceive(Queues.CONTRACT_CREATION_IN, Queues.CONTRACT_CREATION_IN,
				info);

		System.out.println("response: " + response);

	}

}
