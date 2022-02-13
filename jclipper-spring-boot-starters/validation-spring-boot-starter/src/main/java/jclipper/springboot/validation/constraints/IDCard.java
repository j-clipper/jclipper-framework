package jclipper.springboot.validation.constraints;

import jclipper.springboot.validation.RegexConst;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 身份证号验证
 */
@Target({METHOD, FIELD, PARAMETER})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {})
@Pattern(regexp = RegexConst.REGEX_ID_CARD)
public @interface IDCard {

	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "{jclipper.springboot.validation.constraints.IDCard.message}";

	// 约束注解在验证时所属的组别
	Class<?>[] groups() default {};

	// 约束注解的有效负载
	Class<? extends Payload>[] payload() default {};

}
