package ru.obelisk.cucmaxl.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
 
public class NotEmptyConstraintValidator implements ConstraintValidator<NotEmpty, String> {
 
    @Override
    public void initialize(NotEmpty field) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
 
    @Override
    public boolean isValid(String textField, ConstraintValidatorContext cxt) {
        if(textField == null) {
            return false;
        }
        return !textField.isEmpty();
    }
}