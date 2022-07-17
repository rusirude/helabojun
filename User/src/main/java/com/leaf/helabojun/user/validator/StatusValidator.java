package com.leaf.helabojun.user.validator;


import com.leaf.helabojun.user.enums.StatusEnum;
import com.leaf.helabojun.user.validator.impl.StatusValidatorImpl;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = StatusValidatorImpl.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StatusValidator {

    StatusEnum[] anyOf();

    String message() default "must be any of them";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
