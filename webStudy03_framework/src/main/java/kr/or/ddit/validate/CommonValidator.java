package kr.or.ddit.validate;

import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.PlatformResourceBundleLocator;

import kr.or.ddit.vo.MemberVO;

public class CommonValidator<T> {
	
	private Validator validator;
	
	{
		ValidatorFactory factory = Validation.byDefaultProvider()
				 .configure()
				 .messageInterpolator(
						 new ResourceBundleMessageInterpolator(
								 new PlatformResourceBundleLocator("kr.or.ddit.msgs.CustomValidationMessage")
							)
					)
				 .buildValidatorFactory();
		validator = factory.getValidator();
	}
	
	public boolean validate(T member, Map<String, StringBuffer> errors, Class<?>...groups) {
		boolean valid = true;
		
		Set<ConstraintViolation<T>> violations = validator.validate(member, groups);
		valid = violations.size() == 0;
		if(!valid) {
			for(ConstraintViolation<T> violation : violations) {
				String propertyName = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
				String message = violation.getMessage();
				StringBuffer messages = errors.get(propertyName);
				if(messages != null) {
					messages.append(", " + message);
				}else {
					errors.put(propertyName, new StringBuffer(message));
				}
				System.out.println(errors.get(propertyName));
			}
			
		}
		return valid;
	}
}























