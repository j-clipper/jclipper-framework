package jclipper.springboot.validation;


import jclipper.springboot.validation.constraints.EnumValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 枚举值验证
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {

	private Set<String> set;

	@Override
	public void initialize(EnumValue constraintAnnotation) {
		List list = Arrays.asList(constraintAnnotation.set());
		set = new HashSet<String>(list);
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if (value == null) {
			return true;
		}
		if (set.contains(value.toString())) {
			return true;
		}
		return false;
	}
}
