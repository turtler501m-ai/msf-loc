package com.ktmmobile.msf.domains.externalclient.imagesystem.domain.code;

import java.util.Arrays;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DocTypeCd {

    FAMILY_CERTIFICATE("11", "가족관계증명서"),
    FOREIGNER_REGISTRATION_CARD("12", "외국인등록증/거소신고증"),
    BUSINESS_REGISTRATION_CERTIFICATE("13", "사업자등록증"),
    CORPORATE_SEAL_CERTIFICATE("14", "법인인감증명서"),
    POWER_OF_ATTORNEY("15", "위임장(법인인감 날인 포함)"),
    AGENT_ID_CARD("16", "대리인 신분증"),
    EMPLOYMENT_CERTIFICATE("17", "대리인 재직증명서"),
    OWNER_ID_CARD("21", "명의자 신분증"),
    LEGAL_REPRESENTATIVE_ID_CARD("22", "법정대리인 신분증"),
    ETC("99", "기타");

    private final String code;
    private final String title;

    public static DocTypeCd from(String code) {
        return Arrays.stream(values())
            .filter(value -> value.code.equals(code))
            .findFirst()
            .orElse(ETC);
    }
}
