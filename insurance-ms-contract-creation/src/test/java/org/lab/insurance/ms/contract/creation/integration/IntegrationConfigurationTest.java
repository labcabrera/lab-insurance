package org.lab.insurance.ms.contract.creation.integration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.ms.contract.creation.integration.config.ContractCreationIntegrationConfig;
import org.lab.insurance.ms.contract.creation.integration.gateway.ContractCreationGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ContractCreationIntegrationConfig.class })
public class IntegrationConfigurationTest {

	@Autowired
	private ContractCreationGateway gateway;

	@Test
	public void test_load_configuration() {
		Assert.assertNotNull(gateway);

		Contract contract = new Contract();
		contract.setNumber("123");

		Contract result = gateway.process(contract);
		System.out.println(result);
	}

}
