package org.lab.insurance.domain.core.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Constraint(validatedBy = OrderValidator.class)
public @interface ValidOrder {

	String message() default "order.validation.invalid";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};

}
