package ru.obelisk.cucmaxl.web.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = NullOrDigitValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NullOrDigit
{
    String message() default "{field.validation.error.nullordigit}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}