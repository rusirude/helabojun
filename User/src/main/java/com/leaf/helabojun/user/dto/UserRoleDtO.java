package com.leaf.helabojun.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleDtO extends CommonDTO{
    private Long id;
    private String description;
    private String status;

}
