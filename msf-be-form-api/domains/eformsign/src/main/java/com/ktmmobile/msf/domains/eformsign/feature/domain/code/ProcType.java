package com.ktmmobile.msf.domains.eformsign.feature.domain.code;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum ProcType implements CommonEnum {
    REQUEST("RQ", "신청"),
    COMPLETE("CP", "처리"),
    ROLLBACK("BK", "반려");

    private final String code;
    private final String title;
}
