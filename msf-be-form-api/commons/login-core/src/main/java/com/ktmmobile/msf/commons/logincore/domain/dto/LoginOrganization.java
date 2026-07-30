package com.ktmmobile.msf.commons.logincore.domain.dto;

public record LoginOrganization(
    String agentCode,
    String agentName,
    String shopCode,
    String shopName,
    String levelCode
) {

    /**
     * 조직 정보 생성
     *
     * @param agentCode 대리점 코드
     * @param agentName 대리점명
     * @param shopCode 판매점 코드
     * @param shopName 판매점명
     */
    public LoginOrganization(String agentCode, String agentName, String shopCode, String shopName) {
        this(agentCode, agentName, shopCode, shopName, "");
    }

    public LoginOrganization {
        agentCode = valueOrEmpty(agentCode);
        agentName = valueOrEmpty(agentName);
        shopCode = valueOrEmpty(shopCode);
        shopName = valueOrEmpty(shopName);
        levelCode = valueOrEmpty(levelCode);
    }

    /**
     * 빈 조직 정보 생성
     *
     * @return 빈 조직 정보
     */
    public static LoginOrganization empty() {
        return new LoginOrganization("", "", "", "", "");
    }

    /**
     * null 문자열 빈 문자열 변환
     *
     * @param value 원본 문자열
     * @return 변환 문자열
     */
    private static String valueOrEmpty(String value) {
        return value == null ? "" : value;
    }
}
