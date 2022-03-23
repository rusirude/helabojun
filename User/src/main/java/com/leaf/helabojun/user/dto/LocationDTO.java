package com.leaf.helabojun.user.dto;

import com.leaf.helabojun.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LocationDTO extends CommonDTO{
    private Long id;
    private String description;
    private String status;
    private Long locationTypeId;
    private Long locationTypeDescription;
    private Double latitude;
    private Double longitude;
    private User inCharge;


}
