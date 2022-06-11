package com.leaf.helabojun.user.dto.common;

import com.leaf.helabojun.user.enums.ChannelEnum;
import com.leaf.helabojun.user.enums.ResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ListResponseDTO<V>{
    private ChannelEnum channel;
    private ResponseEnum code;
    private String message;
    private V data;
    private Long totalSize;
}
