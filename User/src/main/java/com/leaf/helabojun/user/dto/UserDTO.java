package com.leaf.helabojun.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO extends CommonDTO{

    private Long id;
    private String username;
    private String name;
    private String password;
    private String status;
    private Long userRoleId;
    private String userRoleDescription;
    private Long locationId;
    private String locationDescription;
    private Boolean lock;
}
