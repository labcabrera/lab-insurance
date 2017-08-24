package org.lab.insurance.ms.contract.creation.integration;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.insurance.BaseAsset;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.insurance.OrderDistribution;
import org.lab.insurance.model.insurance.OrderType;
import org.lab.insurance.model.product.Agreement;
import org.lab.insurance.model.product.AgreementRepository;
import org.lab.insurance.ms.contract.creation.config.ContractCreationConfig;
import org.lab.insurance.ms.contract.creation.domain.ContractCreationData;
import org.lab.insurance.ms.contract.creation.integration.gateway.ContractCreationGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ContractCreationConfig.class })
public class IntegrationConfigurationTest {

	@Autowired
	private AgreementRepository agreementRepository;
	@Autowired
	private ContractCreationGateway gateway;

	@Test
	public void test_load_configuration() {

		Agreement agreement = agreementRepository.findByCode("AM01");

		Order order = Order.builder().type(OrderType.INITIAL_PAYMENT).buyDistribution(new ArrayList<>()).build();
		order.getBuyDistribution().add(
				OrderDistribution.builder().asset(new BaseAsset("ASSET01")).percent(new BigDecimal("33.33")).build());
		order.getBuyDistribution().add(
				OrderDistribution.builder().asset(new BaseAsset("ASSET02")).percent(new BigDecimal("33.33")).build());
		order.getBuyDistribution().add(OrderDistribution.builder().asset(new BaseAsset("GUARANTEE01"))
				.percent(new BigDecimal("33.34")).build());
		order.setNetAmount(new BigDecimal("1000"));

		ContractCreationData data = new ContractCreationData();
		data.setInitialPayment(order);
		data.setAgreement(agreement);

		Contract result = gateway.process(data);

		System.out.println("Result: " + result);

	}

}
