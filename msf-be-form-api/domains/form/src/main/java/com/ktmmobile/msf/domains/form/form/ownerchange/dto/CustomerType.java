package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CustomerType {
    FOREIGNER("FN", "외국인", "I"),
    FOREIGNER_MINOR("FM", "외국인 미성년자", "I"),
    CORPORATE_BUSINESS("JP", "법인사업자", "B"),
    NATIVE("NA", "내국인", "I"),
    OTHER("NE", "기타", "G"),
    NATIVE_MINOR("NM", "내국인(미성년자)", "I"),
    INDIVIDUAL_BUSINESS("PP", "개인사업자", "I"),
    GOVERNMENT("GO", "공공기관", "G");

    private final String code;
    private final String name;
    private final String groupType;

    /**
     * 코드(code)를 입력받아 일치하는 CustomerType enum 객체 반환
     */
    public static CustomerType getCustomerTypeByCode(String code) {
        return Arrays.stream(values())
            .filter(type -> type.code.equals(code))
            .findFirst()
            .orElse(null); // 일치하는 값이 없을 경우 null 반환
    }
}
