package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OwnerChangeType {
    SUCCESS("00", "성공"),
    NOT_VALID_NUMBER("01", "유효하지 않은 전화번호"),
    DIFF_CUST_NO("02", "고객번호가 다름"),
    DIFF_BIZ_NO("03", "판매 사업자 코드가 다름"),
    EMPTY("09", "고객 정보와 휴대폰번호가 일치하지 않습니다.\n 휴대폰번호를 다시 확인해 주세요."),
    REAL_USE_DAY_ERROR("10", "실사용기간 90일 이전"),
    STATUS_STOP("11", "정지 회선인 경우"),
    NON_PAY("12", "미납 회선인 경우"),
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
