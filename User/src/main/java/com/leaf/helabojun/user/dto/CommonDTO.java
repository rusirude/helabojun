package com.leaf.helabojun.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommonDTO {
    private String createdBy;
    private Date createdTime;
    private String updatedBy;
    private Date updatedTime;
}
