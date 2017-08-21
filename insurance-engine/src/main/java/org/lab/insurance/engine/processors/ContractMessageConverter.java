package org.lab.insurance.engine.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.HasContract;
import org.lab.insurance.model.common.internal.Message;
import org.lab.insurance.model.contract.Contract;

public class ContractMessageConverter implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Contract payload = null;
		HasContract hasContract = exchange.getIn().getBody(HasContract.class);
		if (hasContract != null && hasContract.getContract() != null) {
			payload = hasContract.getContract();
		}
		if (!exchange.isFailed() && exchange.getIn().getBody() != null) {
			exchange.getIn().setBody(new Message<Contract>().withCode(Message.SUCCESS).withPayload(payload));
		} else {
			exchange.getIn().setBody(new Message<Contract>().withCode(Message.GENERIC_ERROR).withPayload(payload));
		}
	}
}
