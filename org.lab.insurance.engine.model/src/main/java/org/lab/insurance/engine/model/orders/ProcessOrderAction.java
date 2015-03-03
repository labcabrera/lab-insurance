package org.lab.insurance.engine.model.orders;

import org.lab.insurance.engine.model.ActionDefinition;

@ActionDefinition(endpoint = "direct:order_process")
@SuppressWarnings("serial")
public class ProcessOrderAction extends BaseOrderAction {

}
