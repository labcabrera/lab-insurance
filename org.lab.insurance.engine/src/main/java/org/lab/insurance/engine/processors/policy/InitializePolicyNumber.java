package org.lab.insurance.engine.processors.policy;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.lab.insurance.model.HasPolicy;
import org.lab.insurance.model.jpa.Policy;
import org.lab.insurance.services.common.Sequencer;

/**
 * Procesador encargado de establecer el policy.number
 */
public class InitializePolicyNumber implements Processor {

	@Inject
	private Sequencer sequencer;

	@Override
	public void process(Exchange exchange) throws Exception {
		Policy policy = exchange.getIn().getMandatoryBody(HasPolicy.class).getPolicy();
		Long sequence = sequencer.nextSequence(Policy.class.getName());
		String policyNumber = "23" + StringUtils.leftPad(String.valueOf(sequence), 8, "0");
		policy.setNumber(policyNumber);
	}
}
