package com.leaf.helabojun.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRoleAuthorityDTO extends CommonDTO{

    private Long id;
    private Long userRoleId;
    private String userRoleDescription;
    private String authorityCode;
    private String authorityDescription;
    private String status;
}
