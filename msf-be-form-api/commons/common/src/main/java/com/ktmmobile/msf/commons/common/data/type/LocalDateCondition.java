package com.ktmmobile.msf.commons.common.data.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum LocalDateCondition {

    PAST("과거 날짜여야 합니다."),
    PAST_OR_PRESENT("현재 또는 과거 날짜여야 합니다."),
    FUTURE("미래 날짜여야 합니다."),
    FUTURE_OR_PRESENT("현재 또는 미래 날짜여야 합니다."),
    VALID("");

    private final String message;

}
