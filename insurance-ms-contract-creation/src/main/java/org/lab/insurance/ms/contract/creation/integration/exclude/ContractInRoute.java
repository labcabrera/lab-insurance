package org.lab.insurance.ms.contract.creation.integration.exclude;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.lab.insurance.ms.contract.creation.domain.ContractCreationData;
import org.lab.insurance.ms.contract.creation.integration.IntegrationConstants.Channels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
public class ContractInRoute {

	@Autowired
	private Validator validator;

//	@Router(inputChannel = Channels.ContractCreationIn)
	public String filter(Message<ContractCreationData> msg) {
		log.debug("Routing contract creation input message");
		ContractCreationData data = msg.getPayload();

		Set<ConstraintViolation<ContractCreationData>> validation = validator.validate(data);
		if (!validation.isEmpty()) {
			log.debug("Entity has validation errors");
			msg.getHeaders().put("validation-errors", validation);
			return Channels.ContractCreationError;
		}
		return Channels.ContractCreationProcess;
	}
}
