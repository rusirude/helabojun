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
public class LocationDTO extends CommonDTO{

    @NotEmpty(message = "location.description.not.empty", groups = {ValidationGroupOne.class})
    @Max(value = 50, message = "location.description.length.invalid")
    private String description;
    @NotEmpty(message = "location.status.not.empty", groups = {ValidationGroupOne.class})
    @StatusValidator(anyOf = {StatusEnum.ACTIVE, StatusEnum.INACTIVE}, message = "location.status.invalid", groups = {ValidationGroupOne.class})
    private String status;
    @NotEmpty(message = "location.locationType.not.empty", groups = {ValidationGroupOne.class})
    private String locationType;
    private Long locationTypeDescription;
    private Double latitude;
    private Double longitude;
    @NotEmpty(message = "location.inCharge.not.empty", groups = {ValidationGroupOne.class})
    private String inCharge;


}
