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
public class UserRoleDTO extends CommonDTO {
    @NotEmpty(message = "user.role.description.not.empty", groups = {ValidationGroupOne.class})
    @Max(value = 50, message = "user.role.description.length.invalid")
    private String description;
    @NotEmpty(message = "user.role.status.not.empty", groups = {ValidationGroupOne.class})
    @StatusValidator(anyOf = {StatusEnum.ACTIVE, StatusEnum.INACTIVE}, message = "user.role.status.invalid", groups = {ValidationGroupOne.class})
    private String status;

}
