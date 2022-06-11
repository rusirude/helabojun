package com.leaf.helabojun.user.dto.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequestDTO {
    private Integer start;
    private Integer limit;
    private String orderBy;
    private String order;
    private boolean detailed;
    private String key1;
    private String key2;
    private String key3;
}
