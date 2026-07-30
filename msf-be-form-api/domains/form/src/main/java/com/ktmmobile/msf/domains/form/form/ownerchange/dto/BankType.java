package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum BankType {
    SANOP("BNK", "02", "산업은행", "0020000"),
    IBK("BNK", "03", "기업은행", "0030000"),
    KB("BNK", "04", "국민은행", "0040000"),
    SUHYUP("BNK", "07", "수협중앙", "0070000"),
    NONGHYUP("BNK", "11", "농협", "0110000"),
    WOORI("BNK", "20", "우리은행", "0200000"),
    SC("BNK", "23", "SC제일은행", "0230000"),
    CITI("BNK", "27", "시티은행", "0270000"),
    IM_BANK("BNK", "31", "아이엠뱅크", "0310000"),
    BUSAN("BNK", "32", "부산은행", "0320000"),
    KWANGJU("BNK", "34", "광주은행", "0340000"),
    JEJU("BNK", "35", "제주은행", "0350000"),
    JEONBUK("BNK", "37", "전북은행", "0370000"),
    KYONGNAM("BNK", "39", "경남은행", "0390000"),
    KFCC("BNK", "45", "새마을금고", "0450000"),
    CU("BNK", "48", "신협", "0480000"),
    EPOST("BNK", "71", "우체국", "0710000"),
    HANA("BNK", "81", "KEB하나은행", "0810000"),
    SHINHAN("BNK", "88", "신한은행", "0880000"),
    K_BANK("BNK", "89", "케이뱅크", "0890000"),
    KAKAO_BANK("BNK", "90", "카카오뱅크", "0900000"),
    TOSS_BANK("BNK", "92", "토스뱅크", "0920000");

    private final String group;
    private final String code;
    private final String name;
    private final String teleCode;

    /**
     * 은행 코드(code, 예: "04")를 입력받아 일치하는 BankType Enum 반환
     */
    public static BankType getByCode(String code) {
        return Arrays.stream(values())
            .filter(bank -> bank.code.equals(code))
            .findFirst()
            .orElse(null);
    }

    /**
     * 전문 코드(teleCode, 예: "0040000")를 입력받아 일치하는 BankType Enum 반환
     */
    public static BankType getByTeleCode(String teleCode) {
        return Arrays.stream(values())
            .filter(bank -> bank.teleCode.equals(teleCode))
            .findFirst()
            .orElse(null);
    }
}