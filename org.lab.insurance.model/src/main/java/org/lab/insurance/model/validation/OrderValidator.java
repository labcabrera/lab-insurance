package org.lab.insurance.model.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.model.jpa.insurance.OrderProcessInfo;

public class OrderValidator implements ConstraintValidator<ValidOrder, Order> {

	@Override
	public void initialize(ValidOrder arg0) {
	}

	@Override
	public boolean isValid(Order order, ConstraintValidatorContext ctx) {
		boolean hasErrors = false;
		if (order.getProcessInfo() == null) {
			order.setProcessInfo(new OrderProcessInfo());
		}
		if (order.getDates() == null) {
			ctx.buildConstraintViolationWithTemplate("order.validation.missingDates").addConstraintViolation();
			hasErrors = true;
		} else if (order.getDates().getEffective() == null) {
			ctx.buildConstraintViolationWithTemplate("order.validation.missingEffectiveDate").addConstraintViolation();
			hasErrors = true;
		}
		if (order.getType() == null) {
			ctx.buildConstraintViolationWithTemplate("order.validation.missingType").addConstraintViolation();
			hasErrors = true;
		} else {
			switch (order.getType()) {
			case ADDITIONAL_PAYMENT:
			case INITIAL_PAYMENT:
			case REGULAR_PAYMENT:
				validatePayment(order, ctx);
				break;
			default:
				break;
			}
		}
		return !hasErrors;
	}

	private void validatePayment(Order order, ConstraintValidatorContext ctx) {
		// TODO
		// order.getProcessInfo().set
	}

}
