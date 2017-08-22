package org.lab.insurance.ms.contract.creation.amqp;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DummyAmpqReceiver {

	public void handleMessage(Object contract) {
		log.debug("Received contract {}", contract);
	}
}