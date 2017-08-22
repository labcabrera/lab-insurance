package org.lab.insurance.ms.contract.creation.service;

import java.util.Random;

import org.lab.insurance.model.contract.Contract;
import org.springframework.stereotype.Component;

@Component
public class ContractNumberGenerator {

	public void generate(Contract contract) {
		// TODO
		contract.setNumber(String.valueOf(new Random().nextInt(100000)));
	}

}
