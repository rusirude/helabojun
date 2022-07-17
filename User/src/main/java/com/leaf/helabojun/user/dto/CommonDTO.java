package com.leaf.helabojun.user.dto;

import com.leaf.helabojun.user.validator.InvalidInputValidator;
import com.leaf.helabojun.user.validator.group.ValidationGroupOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommonDTO {
    @InvalidInputValidator(groups = {ValidationGroupOne.class})
    private String createdBy;
    @InvalidInputValidator(groups = {ValidationGroupOne.class})
    private LocalDateTime createdTime;
    @InvalidInputValidator(groups = {ValidationGroupOne.class})
    private String updatedBy;
    @InvalidInputValidator(groups = {ValidationGroupOne.class})
    private LocalDateTime updatedTime;
}
