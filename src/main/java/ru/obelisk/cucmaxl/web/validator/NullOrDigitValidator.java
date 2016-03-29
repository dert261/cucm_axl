package ru.obelisk.cucmaxl.web.validator;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NullOrDigitValidator implements ConstraintValidator<NullOrDigit, String>
{
	@Override
    public void initialize(final NullOrDigit constraintAnnotation){    }

    @Override
    public boolean isValid(String value, final ConstraintValidatorContext context)
    {
    	boolean result=true;
    	
    	if(value!=null && value.length()>0){
	        try{
	        	Integer.parseInt((String)value);
	        }catch (final Exception ignore){
	        	result=false;
	        }
    	}    
    	return result;
    }
}