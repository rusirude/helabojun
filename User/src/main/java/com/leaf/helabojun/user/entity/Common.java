package com.leaf.helabojun.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public class Common {

    @Column(name = "created_by", nullable = false, length = 25)
    private String createdBy;
    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;
    @Column(name = "created_sys_time", nullable = false)
    private LocalDateTime createdSysTime;
    @Column(name = "updated_by", nullable = false, length = 25)
    private String updatedBy;
    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;
    @Column(name = "updated_sys_time", nullable = false)
    private LocalDateTime updatedSysTime;
}
