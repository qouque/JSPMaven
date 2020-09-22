package kr.or.ddit.validate.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import kr.or.ddit.validate.rule.TelNumber;

public class TelNumberChecker implements ConstraintValidator<TelNumber, String> {

	private TelNumber constraint;
	
	@Override
	public void initialize(TelNumber constraintAnnotation) {
		this.constraint = constraintAnnotation;
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		
		//String regex = constraint.regex();
		
//		boolean valid = value.matches(regex);
//		
		return false;
	}

}
