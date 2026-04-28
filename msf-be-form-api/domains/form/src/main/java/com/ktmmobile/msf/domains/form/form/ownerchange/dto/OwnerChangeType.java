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
    EMPTY("09", "조회 결과가 없음"),
    REAL_USE_DAY_ERROR("10", "실사용기간 90일 이전"),
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
