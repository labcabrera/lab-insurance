package org.lab.insurance.ms.core.amqp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.lab.insurance.domain.contract.Contract;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AmqpDummyConfig.class)
public class AmqpBasicTest {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void test() {
		Contract contract = new Contract();
		contract.setNumber("12345");

		Object resultFoo = rabbitTemplate.convertSendAndReceive(AmqpDummyConfig.QUEUE_FOO, AmqpDummyConfig.ROUTING_KEY,
				contract);
		Object resultBar = rabbitTemplate.convertSendAndReceive(AmqpDummyConfig.QUEUE_BAR, AmqpDummyConfig.ROUTING_KEY,
				contract);

		System.out.println("foo: " + resultFoo);
		System.out.println("bar: " + resultBar);
	}

}
