package jclipper.springboot.validation.constraints;


import jclipper.springboot.validation.EnumValueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 枚举值验证
 */
@Target({FIELD, METHOD, PARAMETER})
@Retention(RUNTIME)
@Constraint(validatedBy = EnumValueValidator.class)
public @interface EnumValue {

	String[] set() default {};

	String message() default "{jclipper.springboot.validation.constraints.EnumValue.message}";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
