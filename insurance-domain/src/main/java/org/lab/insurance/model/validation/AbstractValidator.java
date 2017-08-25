package org.lab.insurance.model.validation;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

public abstract class AbstractValidator {

	protected <T> void addIfNotNull(List<T> list, T element) {
		if (element != null) {
			list.add(element);
		}
	}

	protected ConstraintViolationBuilder checkPositiveValue(BigDecimal value, String message, ConstraintValidatorContext ctx) {
		ConstraintViolationBuilder result = null;
		if (value != null && value.compareTo(BigDecimal.ZERO) < 0) {
			result = ctx.buildConstraintViolationWithTemplate(message);
		}
		return result;
	}

	protected ConstraintViolationBuilder checkNullValue(Object entity, String message, ConstraintValidatorContext ctx) {
		ConstraintViolationBuilder result = null;
		if (entity != null) {
			result = ctx.buildConstraintViolationWithTemplate(message);
		}
		return result;
	}

	protected ConstraintViolationBuilder checkNotNullValue(Object entity, String message, ConstraintValidatorContext ctx) {
		ConstraintViolationBuilder result = null;
		if (entity == null) {
			result = ctx.buildConstraintViolationWithTemplate(message);
		}
		return result;
	}
}
