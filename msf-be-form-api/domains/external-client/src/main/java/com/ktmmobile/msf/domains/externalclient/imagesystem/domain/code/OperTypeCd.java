package com.ktmmobile.msf.domains.externalclient.imagesystem.domain.code;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;

@Getter
@RequiredArgsConstructor
public enum OperTypeCd {

    NAC("NAC", "신규가입"),
    MNP("MNP", "번호이동"),
    HCN("HCN", "기기변경"),
    HDN("HDN", "기기변경"),
    MCN("MCN", "명의변경");

    private final String code;
    private final String title;

    public static OperTypeCd from(String code) {
        return Arrays.stream(values())
            .filter(value -> value.code.equals(code))
            .findFirst()
            .orElseThrow(() ->
                new SimpleDomainException("지원하지 않는 operTypeCd=" + code));
    }

    public static OperTypeCd fromNullable(String code) {
        if (code == null || code.isBlank()) {
            return null;
        }

        return from(code);
    }
}