package com.leaf.helabojun.user.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommonListDTO<T> {
    private String uuid;
    private T data;

}
