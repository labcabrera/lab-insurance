package org.lab.insurance.ms.core.integration.example;

import java.util.ArrayList;

import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.model.insurance.BaseAsset;
import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.insurance.OrderDistribution;
import org.lab.insurance.model.insurance.OrderType;
import org.lab.insurance.ms.core.integration.example.gateway.ExampleContractService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * http://docs.spring.io/spring-integration/reference/html/configuration.html
 */
@SpringBootApplication
@EnableMongoRepositories("org.lab.insurance.model.insurance")
public class IntegrationDemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(IntegrationDemoApplication.class, args);

		ExampleContractService service = ctx.getBean(ExampleContractService.class);

		String isin = "ASSET01";
		Order order = Order.builder().type(OrderType.INITIAL_PAYMENT).buyDistribution(new ArrayList<>()).build();
		order.getBuyDistribution().add(OrderDistribution.builder().asset(new BaseAsset(isin)).build());
		Contract contract = new Contract();
		contract.setOrders(new ArrayList<>());
		contract.getOrders().add(order);

		Contract result = service.store(contract);

		System.out.println("Result: " + result);
	}

}
