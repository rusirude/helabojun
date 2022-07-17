package com.leaf.helabojun.user.validator.impl;

import com.leaf.helabojun.user.enums.StatusEnum;
import com.leaf.helabojun.user.validator.StatusValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StatusValidatorImpl implements ConstraintValidator<StatusValidator, String> {

    private List<String> status;

    @Override
    public void initialize(StatusValidator constraintAnnotation) {
        this.status = Arrays.stream(constraintAnnotation.anyOf()).map(StatusEnum::getCode).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return status.contains(s);
    }
}
