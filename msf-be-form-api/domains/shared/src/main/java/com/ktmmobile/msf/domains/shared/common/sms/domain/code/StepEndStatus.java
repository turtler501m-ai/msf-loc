package com.ktmmobile.msf.domains.shared.common.sms.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum StepEndStatus implements CommonEnum {
    Y("Y", "예"),
    N("N", "아니오");

    private final String code;
    private final String title;
}
