package com.leaf.helabojun.user.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public class Common {

    @Column(name = "created_by", nullable = false, length = 25)
    private String createdBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", nullable = false)
    private Date createdTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_sys_time", nullable = false)
    private Date createdSysTime;
    @Column(name = "updated_by", nullable = false, length = 25)
    private String updatedBy;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time", nullable = false)
    private Date updatedTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_sys_time", nullable = false)
    private Date updatedSysTime;
}
