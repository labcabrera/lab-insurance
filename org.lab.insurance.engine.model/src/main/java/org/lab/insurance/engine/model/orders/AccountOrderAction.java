package org.lab.insurance.engine.model.orders;

import org.lab.insurance.engine.model.ActionDefinition;

@ActionDefinition(endpoint = "direct:order_accounting")
@SuppressWarnings("serial")
public class AccountOrderAction extends BaseOrderAction {

}
