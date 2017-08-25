package org.lab.insurance.model.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.lab.insurance.model.insurance.Order;
import org.lab.insurance.model.insurance.OrderProcessInfo;

public class OrderValidator extends AbstractValidator implements ConstraintValidator<ValidOrder, Order> {

	@Override
	public void initialize(ValidOrder arg0) {
	}

	@Override
	public boolean isValid(Order order, ConstraintValidatorContext ctx) {
		if (order.getProcessInfo() == null) {
			order.setProcessInfo(new OrderProcessInfo());
		}
		List<ConstraintViolationBuilder> errors = new ArrayList<ConstraintViolationBuilder>();
		addIfNotNull(errors, checkNotNullValue(order.getDates(), "order.validation.missingDates", ctx));
		addIfNotNull(errors, checkNotNullValue(order.getType(), "order.validation.missingType", ctx));
		if (order.getDates() != null) {
			addIfNotNull(errors, checkNotNullValue(order.getDates().getEffective(), "order.validation.missingEffectiveDate", ctx));
		}
		if (order.getType() != null) {
			switch (order.getType()) {
			case ADDITIONAL_PAYMENT:
			case INITIAL_PAYMENT:
			case REGULAR_PAYMENT:
				validatePayment(order, ctx, errors);
				break;
			default:
				break;
			}
		}
		if (errors.isEmpty()) {
			return true;
		} else {
			for (ConstraintViolationBuilder i : errors) {
				i.addConstraintViolation();
			}
			return false;
		}
	}

	// TODO
	private void validatePayment(Order order, ConstraintValidatorContext ctx, List<ConstraintViolationBuilder> errors) {
		if (order.getBuyDistribution() == null || order.getBuyDistribution().isEmpty()) {
			errors.add(ctx.buildConstraintViolationWithTemplate("order.validation.missingBuyDistribution"));
		}
	}

}
