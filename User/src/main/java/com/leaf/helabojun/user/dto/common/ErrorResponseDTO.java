package com.leaf.helabojun.user.dto.common;

import com.leaf.helabojun.user.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponseDTO<T> {
    private ResponseEnum code;
    private T message;
    private LocalDateTime time;
    private String detail;
}
