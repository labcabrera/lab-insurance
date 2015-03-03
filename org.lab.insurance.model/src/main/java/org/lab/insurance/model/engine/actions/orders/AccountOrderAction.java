package org.lab.insurance.model.engine.actions.orders;

import org.lab.insurance.model.engine.ActionDefinition;

@ActionDefinition(endpoint = "direct:order_accounting")
@SuppressWarnings("serial")
public class AccountOrderAction extends BaseOrderAction {

}
