package org.lab.insurance.engine.processors.policy;

import javax.inject.Inject;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.lab.insurance.model.HasContract;
import org.lab.insurance.model.jpa.contract.Contract;
import org.lab.insurance.services.common.Sequencer;

/**
 * Procesador encargado de establecer el policy.number
 */
public class InitializeContractNumber implements Processor {

	@Inject
	private Sequencer sequencer;

	@Override
	public void process(Exchange exchange) throws Exception {
		Contract contract = exchange.getIn().getMandatoryBody(HasContract.class).getContract();
		Long sequence = sequencer.nextSequence(Contract.class.getName());
		String policyNumber = "23" + StringUtils.leftPad(String.valueOf(sequence), 8, "0");
		contract.setNumber(policyNumber);
	}
}
