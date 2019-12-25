package com.example.demo.web.framework.annotction;


import com.example.demo.web.framework.annotction.validator.PhoneNumberValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * @author wuhengzhen
 */
@Documented
@Constraint(validatedBy = PhoneNumberValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneNumber {

    String message() default "手机号无效";

    Class[] groups() default {};

    Class[] payload() default {};
}
