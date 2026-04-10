package com.ktmmobile.msf.commons.common.servicelock.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceLockType {

    TEST("Test"),
    ;

    private final String code;
}
