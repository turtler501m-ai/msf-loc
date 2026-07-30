package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OwnerChangeType {
    SUCCESS("00", "성공"),
    NOT_VALID_NUMBER("01", "유효하지 않은 휴대폰번호 입니다."),
    DIFF_CUST_NO("02", "고객번호가 일치하지 않습니다."),
    DIFF_BIZ_NO("03", "판매 사업자가 일치하지 않습니다."),
    EMPTY("09", "고객 정보와 휴대폰번호가 일치하지 않습니다.\n 휴대폰번호를 다시 확인해 주세요."),
    REAL_USE_DAY_ERROR("100", "실사용기간 90일 이전 이므로 명의변경이 불가합니다."),
    REAL_USE_DAY_EMPTY("101", "실사용자 회선 정보가 존재하지 않습니다."),
    STATUS_STOP("11", "정지 회선은 명의변경이 불가합니다."),
    EMPTY_PLAN("13", "요금제 정보가 없습니다."),
    NON_PAY("12", "미납 회선은 명의변경이 불가합니다."),
    ERROR("99", "에러");

    private final String code;
    private final String message;

    public static OwnerChangeType fromCode(String code) {
        for (OwnerChangeType r: values()) {
            if (r.code.equals(code)) {
                return r;
            }
        }
        return ERROR;
    }

}
