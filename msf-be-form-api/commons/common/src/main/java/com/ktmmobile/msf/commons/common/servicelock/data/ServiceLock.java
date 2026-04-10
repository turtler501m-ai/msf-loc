package com.ktmmobile.msf.commons.common.servicelock.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ServiceLock {

    private final String lockType;
    private final String lockKey;

    public static ServiceLock of(String lockType, String lockKey) {
        return new ServiceLock(lockType, lockKey);
    }

    @JsonIgnore
    public String getLockTypeKey() {
        return lockType + ":" + lockKey;
    }
}
