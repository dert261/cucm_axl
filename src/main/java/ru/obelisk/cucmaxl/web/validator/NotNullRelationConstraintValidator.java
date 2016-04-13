package ru.obelisk.cucmaxl.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import ru.obelisk.cucmaxl.database.models.entity.utils.BaseEntity;
 
public class NotNullRelationConstraintValidator implements ConstraintValidator<NotNullRelationField, Object> {
 
    @Override
    public void initialize(NotNullRelationField field) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
 
    @Override
    public boolean isValid(Object field, ConstraintValidatorContext cxt) {
    	if(field == null) {
            return false;
        }
    	
    	if(field instanceof BaseEntity){
    		return ((BaseEntity) field).getId()==0 ? false : true;
    	}
        return true;
    }
}