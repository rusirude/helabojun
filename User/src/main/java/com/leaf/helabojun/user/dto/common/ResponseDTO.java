package com.leaf.helabojun.user.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class ResponseDTO<U,V>{
    private String channel;
    private String code;
    private String message;
    private U key;
    private V data;
}
