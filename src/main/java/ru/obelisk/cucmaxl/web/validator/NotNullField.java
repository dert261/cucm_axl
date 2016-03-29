package ru.obelisk.cucmaxl.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotNullConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotNullField {
	String message() default "field.validation.error.notnull";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
