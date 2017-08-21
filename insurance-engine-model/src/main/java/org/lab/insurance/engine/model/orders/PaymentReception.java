package org.lab.insurance.engine.model.orders;

import org.lab.insurance.engine.model.ActionDefinition;

@ActionDefinition(endpoint = "direct:payment_reception")
@SuppressWarnings("serial")
public class PaymentReception extends OrderBaseAction {

}
