package org.lab.insurance.engine.model.orders;

import java.util.Date;

import org.lab.insurance.domain.HasOrder;
import org.lab.insurance.domain.insurance.Order;
import org.lab.insurance.engine.model.ActionEntity;

@SuppressWarnings("serial")
public abstract class OrderBaseAction implements ActionEntity<Order>, HasOrder {

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
	public <T extends OrderBaseAction> T withOrderId(String orderId) {
		if (order == null) {
			order = new Order();
		}
		order.setId(orderId);
		return (T) this;
	}

	@SuppressWarnings("unchecked")
	public <T extends OrderBaseAction> T withActionDate(Date date) {
		this.actionDate = date;
		return (T) this;
	}
}
