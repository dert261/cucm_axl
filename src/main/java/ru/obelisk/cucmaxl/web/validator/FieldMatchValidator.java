package ru.obelisk.cucmaxl.web.validator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object>
{
	private static Logger logger = LogManager.getLogger(FieldMatchValidator.class);
	
    private String firstFieldName;
    private String secondFieldName;
    
    @Override
    public void initialize(final FieldMatch constraintAnnotation)
    {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context)
    {
        try{
            final Object firstObj = BeanUtils.getProperty(value, firstFieldName);
            final Object secondObj = BeanUtils.getProperty(value, secondFieldName);

            return firstObj != null && firstObj.equals(secondObj);
        }
        catch (final Exception ignore){
            logger.warn("Validation error: {}",ignore);
        }
        return true;
    }
    /*
    public boolean isValid(Object object, ConstraintValidatorContext constraintContext) {
    	if (object == null) {
    		return true;
        }
        try {
        	/*for (String field : fieldNames) {
        		Object property = tryGetField(object, field, "get", "is");
        		if (property != null) {
        			return true;
        		}
        	}*/
    /*    	return tryGetField(object, fieldNames[0], "get", "is").equals(tryGetField(object, fieldNames[1], "get", "is"));
        } catch (Exception e) {
          logger.error("Exception during AtLeastOne validation", e);
        }
        return false;
    }
   
    private Object tryGetField(Object object, String field, String... prefixes)
          throws IllegalAccessException, InvocationTargetException {
    	for (String prefix: prefixes) {
    		try {
    			return object.getClass().getMethod(prefix + field.substring(0, 1).toUpperCase() + field.substring(1))
    					.invoke(object);
    		} catch (NoSuchMethodException ignore) {
    			// Nothing to do
    		}
        }
        return null;
    }*/
    
}