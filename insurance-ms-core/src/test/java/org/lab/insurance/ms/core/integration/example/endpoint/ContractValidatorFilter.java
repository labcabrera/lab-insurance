package org.lab.insurance.ms.core.integration.example.endpoint;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.lab.insurance.model.contract.Contract;
import org.lab.insurance.ms.core.integration.example.DemoConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Filter;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ContractValidatorFilter {

	@Autowired
	private Validator validator;

	@Filter(inputChannel = DemoConstants.CHANNEL_RESPONSE, outputChannel = DemoConstants.CHANNEL_STORE)
	public boolean filterCourse(Message<Contract> msg) {
		log.info("Validating contract (response >> store)");

		Contract contract = msg.getPayload();
		Set<ConstraintViolation<Contract>> validationResult = validator.validate(contract,
				Contract.ValidationContext.Insert.class);

		if (validationResult.isEmpty()) {
			log.warn("Validation errors");
			return false;
		}
		return true;
	}
}
