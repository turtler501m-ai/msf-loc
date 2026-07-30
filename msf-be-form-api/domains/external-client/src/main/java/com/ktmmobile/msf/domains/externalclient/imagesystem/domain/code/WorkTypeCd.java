package com.ktmmobile.msf.domains.externalclient.imagesystem.domain.code;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.exception.SimpleDomainException;

@Getter
@RequiredArgsConstructor
public enum WorkTypeCd {

    JOIN("S0001", "가입"),
    CHANGE("S0002", "변경"),
    OWNER_CHANGE("S0003", "명의변경"),
    DEVICE_USIM_CHANGE("S0004", "단말기/USIM변경"),
    TERMINATION("S0005", "해지"),
    NUMBER_PORTING("S0013", "무선번호이동"),
    PHONE_DEVICE_CHANGE("S0015", "휴대폰기변");

    private final String code;
    private final String title;

    public static WorkTypeCd from(String code) {
        return Arrays.stream(values())
            .filter(value -> value.code.equals(code))
            .findFirst()
            .orElseThrow(() ->
                new SimpleDomainException("지원하지 않는 workCd=" + code)
            );
    }
}
