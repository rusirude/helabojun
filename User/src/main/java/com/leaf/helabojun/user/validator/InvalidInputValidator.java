package com.leaf.helabojun.user.validator;


import com.leaf.helabojun.user.validator.impl.InvalidInputValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = InvalidInputValidatorImpl.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InvalidInputValidator {


    String message() default "invalid.input.field";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
