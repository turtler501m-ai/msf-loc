package com.ktmmobile.msf.domains.externalclient.imagesystem.domain.code;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;

@Getter
@RequiredArgsConstructor
public enum FormTypeCd {

    NEW_CHANGE("newchange", "신규/변경"),
    SERVICE_CHANGE("servicechange", "서비스변경"),
    OWNER_CHANGE("ownerchange", "명의변경"),
    TERMINATION("termination", "해지");

    private final String code;
    private final String title;

    public static FormTypeCd from(String code) {
        return Arrays.stream(values())
            .filter(value -> value.code.equals(code))
            .findFirst()
            .orElseThrow(() ->
                new SimpleDomainException("지원하지 않는 formTypeCd=" + code));
    }
}
