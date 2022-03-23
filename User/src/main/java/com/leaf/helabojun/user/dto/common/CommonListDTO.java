package com.leaf.helabojun.user.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonListDTO<T> {
    private String uuid;
    private T data;

}
