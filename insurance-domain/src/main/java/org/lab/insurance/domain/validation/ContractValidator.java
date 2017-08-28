package org.lab.insurance.domain.validation;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.lab.insurance.domain.contract.Contract;
import org.lab.insurance.domain.insurance.Order;
import org.lab.insurance.domain.insurance.OrderType;

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

		List<Order> initialPayments = policy.getOrders().stream()
				.filter(x -> OrderType.INITIAL_PAYMENT.equals(x.getType())).collect(Collectors.toList());

		if (initialPayments.isEmpty()) {
			ctx.buildConstraintViolationWithTemplate("policy.validation.missingInitialPayment")
					.addConstraintViolation();
			hasErrors = true;
		}
		return !hasErrors;
	}
}
