package org.lab.insurance.engine.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.lab.insurance.model.HasPolicy;
import org.lab.insurance.model.common.Message;
import org.lab.insurance.model.jpa.Policy;

public class PolicyMessageConverter implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		Policy payload = null;
		HasPolicy hasPolicy = exchange.getIn().getBody(HasPolicy.class);
		if (hasPolicy != null && hasPolicy.getPolicy() != null) {
			payload = hasPolicy.getPolicy();
		}
		if (!exchange.isFailed() && exchange.getIn().getBody() != null) {
			exchange.getIn().setBody(new Message<Policy>().withCode(Message.SUCCESS).withPayload(payload));
		} else {
			exchange.getIn().setBody(new Message<Policy>().withCode(Message.GENERIC_ERROR).withPayload(payload));
		}
	}
}
