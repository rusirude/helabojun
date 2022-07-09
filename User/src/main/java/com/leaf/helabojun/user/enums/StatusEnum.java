package com.leaf.helabojun.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  StatusEnum {

    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE"),
    DELETE("DELETE");

    private String code;


}
