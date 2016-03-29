package ru.obelisk.cucmaxl.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
 
public class NotNullConstraintValidator implements ConstraintValidator<NotNullField, Object> {
 
    @Override
    public void initialize(NotNullField field) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
 
    @Override
    public boolean isValid(Object field, ConstraintValidatorContext cxt) {
        if(field == null) {
            return false;
        }
        return true;
    }
}