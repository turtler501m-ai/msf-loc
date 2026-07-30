package com.ktmmobile.msf.domains.login.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;

@Getter
@RequiredArgsConstructor
public enum UserStatusCode implements CommonEnum {

    ACTIVE("A", "활성"),
    LOCKED("C", "잠금"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "Invalid UserStatusCode");

    private final String code;
    private final String title;

    /**
     * 활성 상태 여부 확인
     *
     * @return 활성 상태 여부
     */
    public boolean isActive() {
        return this == ACTIVE;
    }

    /**
     * 코드 기준 활성 상태 여부 확인
     *
     * @param code 사용자 상태 코드
     * @return 활성 상태 여부
     */
    public static boolean isActive(String code) {
        return valueOfCode(code).isActive();
    }

    /**
     * 코드 기준 사용자 상태 코드 변환
     *
     * @param code 사용자 상태 코드
     * @return 사용자 상태 코드 enum
     */
    @JsonCreator
    public static UserStatusCode valueOfCode(String code) {
        return CommonEnum.valueOfCode(UserStatusCode.class, code, getInvalidValue());
    }

    /**
     * 유효한 enum 여부 확인
     *
     * @return 유효 여부
     */
    @Override
    public boolean isValid() {
        return this != getInvalidValue();
    }

    /**
     * 유효하지 않은 enum 값 조회
     *
     * @return 유효하지 않은 enum 값
     */
    public static UserStatusCode getInvalidValue() {
        return UNDEFINED;
    }
}
