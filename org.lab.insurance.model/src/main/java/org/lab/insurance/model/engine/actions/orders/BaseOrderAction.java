package org.lab.insurance.model.engine.actions.orders;

import java.util.Date;

import org.lab.insurance.model.HasOrder;
import org.lab.insurance.model.engine.ActionEntity;
import org.lab.insurance.model.jpa.insurance.Order;

@SuppressWarnings("serial")
public abstract class BaseOrderAction implements ActionEntity<Order>, HasOrder {

	protected Order order;
	protected Date actionDate;

	@Override
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseOrderAction> T withOrderId(String orderId) {
		if (order == null) {
			order = new Order();
		}
		order.setId(orderId);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public <T extends BaseOrderAction> T withActionDate(Date date) {
		this.actionDate = date;
		return (T) this;
	}
}
