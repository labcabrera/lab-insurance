package org.lab.insurance.model.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.lab.insurance.model.jpa.contract.Contract;
import org.lab.insurance.model.jpa.insurance.Order;
import org.lab.insurance.model.jpa.insurance.OrderType;
import org.lab.insurance.model.matchers.OrderTypeMatcher;

import ch.lambdaj.Lambda;

public class ContractValidator implements ConstraintValidator<ValidContract, Contract> {

	@Override
	public void initialize(ValidContract validPolicy) {
	}

	@Override
	public boolean isValid(Contract policy, ConstraintValidatorContext ctx) {
		boolean hasErrors = false;
		if (policy.getAgreement() == null) {
			ctx.buildConstraintViolationWithTemplate("policy.validation.missingAgreement").addConstraintViolation();
			hasErrors = true;
		}
		if (policy.getRelations() == null || policy.getRelations().isEmpty()) {
			ctx.buildConstraintViolationWithTemplate("policy.validation.missingRelations").addConstraintViolation();
			hasErrors = true;
		}
		for (Order order : policy.getOrders()) {
			order.setContract(policy);
		}
		List<Order> initialPayments = Lambda.select(policy.getOrders(), new OrderTypeMatcher(OrderType.INITIAL_PAYMENT));
		if (initialPayments.isEmpty()) {
			ctx.buildConstraintViolationWithTemplate("policy.validation.missingInitialPayment").addConstraintViolation();
			hasErrors = true;
		}
		return !hasErrors;
	}
}
