package com.leaf.helabojun.user.validator.impl;

import com.leaf.helabojun.user.validator.InvalidInputValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class InvalidInputValidatorImpl implements ConstraintValidator<InvalidInputValidator, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return Objects.isNull(o);
    }
}
