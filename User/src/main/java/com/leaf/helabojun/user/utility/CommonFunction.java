package com.leaf.helabojun.user.utility;

import com.leaf.helabojun.user.entity.Common;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class CommonFunction {

    public <T extends Common> void populateInsert(T entity, String username) {
        entity.setCreatedBy(username);
        entity.setCreatedTime(getSystemDate());
        entity.setCreatedSysTime(LocalDateTime.now());

        entity.setUpdatedBy(username);
        entity.setUpdatedTime(getSystemDate());
        entity.setUpdatedSysTime(LocalDateTime.now());
    }

    public <T extends Common> void populateUpdate(T entity, String username) {
        entity.setUpdatedBy(username);
        entity.setUpdatedTime(getSystemDate());
        entity.setUpdatedSysTime(LocalDateTime.now());
    }

    public String getUuid() {
        return UUID.randomUUID().toString();
    }

    public LocalDateTime getSystemDate() {
        return LocalDateTime.now();
    }
}
