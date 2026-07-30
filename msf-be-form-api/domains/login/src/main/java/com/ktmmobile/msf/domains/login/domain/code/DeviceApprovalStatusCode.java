package com.ktmmobile.msf.domains.login.domain.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;

@Getter
@RequiredArgsConstructor
public enum DeviceApprovalStatusCode implements CommonEnum {

    APPROVED("A", "승인"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "Invalid DeviceApprovalStatusCode");

    private final String code;
    private final String title;

    /**
     * 승인 상태 여부 확인
     *
     * @return 승인 상태 여부
     */
    public boolean isApproved() {
        return this == APPROVED;
    }

    /**
     * 코드 기준 승인 상태 여부 확인
     *
     * @param code 단말 승인 상태 코드
     * @return 승인 상태 여부
     */
    public static boolean isApproved(String code) {
        return valueOfCode(code).isApproved();
    }

    /**
     * 코드 기준 단말 승인 상태 코드 변환
     *
     * @param code 단말 승인 상태 코드
     * @return 단말 승인 상태 코드 enum
     */
    @JsonCreator
    public static DeviceApprovalStatusCode valueOfCode(String code) {
        return CommonEnum.valueOfCode(DeviceApprovalStatusCode.class, code, getInvalidValue());
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
    public static DeviceApprovalStatusCode getInvalidValue() {
        return UNDEFINED;
    }
}
