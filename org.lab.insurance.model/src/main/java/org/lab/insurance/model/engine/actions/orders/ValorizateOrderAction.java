package org.lab.insurance.model.engine.actions.orders;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.lab.insurance.model.engine.ActionDefinition;

@ActionDefinition(endpoint = "direct:order_valorizarion")
@SuppressWarnings("serial")
public class ValorizateOrderAction extends BaseOrderAction {

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("ValorizateOrder/");
		sb.append(order != null ? order.getId() : "<null>");
		sb.append("/");
		sb.append(actionDate != null ? DateFormatUtils.ISO_DATE_FORMAT.format(actionDate) : "<null>");
		return sb.toString();
	}
}
