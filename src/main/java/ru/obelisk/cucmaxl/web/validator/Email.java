package ru.obelisk.cucmaxl.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmailConstraintValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {
	String message() default "field.validation.error.email";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
