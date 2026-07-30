package com.ktmmobile.msf.domains.cache.worknotice.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum WorkNoticeFormType implements CommonEnum {
    ALL("0", "전체"),
    NEW_CHANGE("1", "신규/변경"),
    SERVICE_CHANGE("2", "서비스변경"),
    OWNER_CHANGE("3", "명의변경"),
    TERMINATION("4", "서비스해지");

    private final String code;
    private final String title;

    @JsonCreator
    public static WorkNoticeFormType from(String code) {
        for (WorkNoticeFormType type : WorkNoticeFormType.values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }
}
