package com.leaf.helabojun.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  StatusEnum {

    ACTIVE("ACTIVE","Active"),
    INACTIVE("INACTIVE","In Active"),
    DELETE("DELETE","Delete");

    private String code;
    private String description;


}
