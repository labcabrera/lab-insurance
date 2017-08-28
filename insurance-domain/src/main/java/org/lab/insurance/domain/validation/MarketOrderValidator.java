package org.lab.insurance.domain.validation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.lab.insurance.domain.Constants;
import org.lab.insurance.domain.insurance.MarketOrder;
import org.lab.insurance.domain.insurance.MarketOrderSource;

public class MarketOrderValidator extends AbstractValidator
		implements ConstraintValidator<ValidMarketOrder, MarketOrder> {

	@Override
	public void initialize(ValidMarketOrder value) {
	}

	@Override
	public boolean isValid(MarketOrder entity, ConstraintValidatorContext ctx) {
		List<ConstraintViolationBuilder> errors = new ArrayList<ConstraintViolationBuilder>();
		if (entity.getSource() == MarketOrderSource.AMOUNT
				&& (entity.getGrossAmount() == null || entity.getGrossAmount().equals(BigDecimal.ZERO))) {
			errors.add(ctx.buildConstraintViolationWithTemplate("marketOrder.validation.missingAmount"));
		} else if (entity.getSource() == MarketOrderSource.UNITS
				&& (entity.getUnits() == null || entity.getUnits().equals(BigDecimal.ZERO))) {
			errors.add(ctx.buildConstraintViolationWithTemplate("marketOrder.validation.missingUnits"));
		}
		addIfNotNull(errors,
				checkPositiveValue(entity.getGrossAmount(), "marketOrder.validation.negativeGrossAmount", ctx));
		addIfNotNull(errors,
				checkPositiveValue(entity.getNetAmount(), "marketOrder.validation.negativeNetAmount", ctx));
		addIfNotNull(errors, checkPositiveValue(entity.getUnits(), "marketOrder.validation.negativeUnits", ctx));
		addIfNotNull(errors, checkPositiveValue(entity.getNav(), "marketOrder.validation.negativeNav", ctx));
		// Comprobamos la integridad de las fechas y los estados
		if (entity.getCurrentState() != null && entity.getDates() != null) {
			switch (entity.getCurrentState().getCode()) {
			case Constants.MarketOrderStates.INITIAL:
				addIfNotNull(errors, checkNullValue(entity.getDates().getProcessed(),
						"marketOrder.validation.stateInitialWithProcessedDate", ctx));
				addIfNotNull(errors, checkNullValue(entity.getDates().getValued(),
						"marketOrder.validation.stateInitialWithValuedDate", ctx));
				addIfNotNull(errors, checkNullValue(entity.getDates().getAccounted(),
						"marketOrder.validation.stateInitialWithAcountedDate", ctx));
			case Constants.MarketOrderStates.PROCESSED:
				addIfNotNull(errors, checkNotNullValue(entity.getDates().getProcessed(),
						"marketOrder.validation.stateProcessedWithoutProcessedDate", ctx));
				addIfNotNull(errors, checkNotNullValue(entity.getDates().getValueDate(),
						"marketOrder.validation.stateProcessedWithoutValueDate", ctx));
				addIfNotNull(errors, checkNullValue(entity.getDates().getAccounted(),
						"marketOrder.validation.stateProcessedWithAcountedDate", ctx));
				addIfNotNull(errors, checkNullValue(entity.getDates().getValued(),
						"marketOrder.validation.stateProcessedWithValuedDate", ctx));
				break;
			case Constants.MarketOrderStates.VALUED:
				addIfNotNull(errors, checkNotNullValue(entity.getDates().getProcessed(),
						"marketOrder.validation.stateValuedWithoutProcessedDate", ctx));
				addIfNotNull(errors, checkNotNullValue(entity.getDates().getValueDate(),
						"marketOrder.validation.stateValuedWithoutValueDate", ctx));
				addIfNotNull(errors, checkNotNullValue(entity.getDates().getValued(),
						"marketOrder.validation.stateValuedWithoutValuedDate", ctx));
				addIfNotNull(errors, checkNullValue(entity.getDates().getAccounted(),
						"marketOrder.validation.stateValuedWithAccountedDate", ctx));
				break;
			case Constants.MarketOrderStates.ACCOUNTED:
				addIfNotNull(errors, checkNotNullValue(entity.getDates().getProcessed(),
						"marketOrder.validation.stateAccountedWithoutProcessedDate", ctx));
				addIfNotNull(errors, checkNotNullValue(entity.getDates().getValueDate(),
						"marketOrder.validation.stateAccountedWithoutValueDate", ctx));
				addIfNotNull(errors, checkNotNullValue(entity.getDates().getValued(),
						"marketOrder.validation.stateAccountedWithoutValued", ctx));
				addIfNotNull(errors, checkNotNullValue(entity.getDates().getAccounted(),
						"marketOrder.validation.stateAccountedWithoutAccountedDate", ctx));
				break;
			default:
				break;
			}
		}
		if (errors.isEmpty()) {
			return true;
		} else {
			for (ConstraintViolationBuilder builder : errors) {
				builder.addConstraintViolation();

			}
			return false;
		}
	}
}
