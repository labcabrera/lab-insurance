package org.lab.insurance.ms.contract.creation.amqp;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ContractCreationListener implements MessageListener {

	// TODO
	@Override
	public void onMessage(Message message) {
		log.debug("Received contract {}", message);
	}
}