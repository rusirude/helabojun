package com.leaf.helabojun.user.dto;

import com.leaf.helabojun.user.enums.StatusEnum;
import com.leaf.helabojun.user.validator.StatusValidator;
import com.leaf.helabojun.user.validator.group.ValidationGroupOne;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class SectionDTO extends CommonDTO {
    @NotEmpty(message = "section.description.not.empty", groups = {ValidationGroupOne.class})
    @Max(value = 50, message = "section.description.length.invalid")
    private String description;
    @NotEmpty(message = "section.status.not.empty", groups = {ValidationGroupOne.class})
    @StatusValidator(anyOf = {StatusEnum.ACTIVE, StatusEnum.INACTIVE}, message = "section.status.invalid", groups = {ValidationGroupOne.class})
    private String status;

}
