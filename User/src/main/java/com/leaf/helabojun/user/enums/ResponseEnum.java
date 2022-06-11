package com.leaf.helabojun.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseEnum {

    SUCCESS("SUCCESS"),
    ERROR("ERROR");

    private String code;


}