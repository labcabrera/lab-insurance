package org.lab.insurance.ms.core;

public interface InsuranceCoreConstants {

	public interface Queues {
		String CONTRACT_CREATION_IN = "queue/lab-insurance/contract-creation/in";
		String CONTRACT_CREATION_OUT = "queue/lab-insurance/contract-creation/out";
		String TOPIC_EXCHANGE = "topic/lab-insurance/exchange";
	}

}
