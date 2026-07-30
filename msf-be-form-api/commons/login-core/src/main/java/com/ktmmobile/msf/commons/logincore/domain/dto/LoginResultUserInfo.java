package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.util.LinkedHashMap;
import java.util.Map;

public record LoginResultUserInfo(
    String userId,
    String userName,
    String phoneNumber,
    String clientIp,
    LoginOrganization organization,
    Map<String, Object> attributes
) {

    public LoginResultUserInfo {
        organization = organization == null ? LoginOrganization.empty() : organization;
        attributes = attributes == null ? new LinkedHashMap<>() : new LinkedHashMap<>(attributes);
    }

    /**
     * 조직 코드 기반 로그인 결과 사용자 정보 생성
     *
     * @param userId 사용자 ID
     * @param userName 사용자명
     * @param phoneNumber 휴대폰번호
     * @param clientIp 클라이언트 IP
     * @param agentCode 대리점 코드
     * @param agentName 대리점명
     * @param shopCode 판매점 코드
     * @param shopName 판매점명
     * @param attributes 부가 속성
     */
    public LoginResultUserInfo(
        String userId,
        String userName,
        String phoneNumber,
        String clientIp,
        String agentCode,
        String agentName,
        String shopCode,
        String shopName,
        Map<String, Object> attributes
    ) {
        this(userId, userName, phoneNumber, clientIp, new LoginOrganization(agentCode, agentName, shopCode, shopName), attributes);
    }

    /**
     * 필수 조치 결과 사용자 정보 변환
     *
     * @param required 필수 조치 결과
     * @return 로그인 결과 사용자 정보
     */
    public static LoginResultUserInfo from(LoginActionRequired required) {
        return new LoginResultUserInfo(
            required.userId(),
            required.userName(),
            required.phoneNumber(),
            required.clientIp(),
            required.organization(),
            required.attributes()
        );
    }

    /**
     * 토큰 발급 가능 결과 사용자 정보 변환
     *
     * @param ready 토큰 발급 가능 결과
     * @return 로그인 결과 사용자 정보
     */
    public static LoginResultUserInfo from(LoginSessionReady ready) {
        return new LoginResultUserInfo(
            ready.userId(),
            ready.userName(),
            ready.phoneNumber(),
            ready.clientIp(),
            ready.organization(),
            ready.attributes()
        );
    }

    /**
     * 2FA 필요 결과 사용자 정보 변환
     *
     * @param required 2FA 필요 결과
     * @return 로그인 결과 사용자 정보
     */
    public static LoginResultUserInfo from(LoginTwoFactorRequired required) {
        return new LoginResultUserInfo(
            required.userId(),
            required.userName(),
            required.phoneNumber(),
            required.clientIp(),
            required.organization(),
            required.attributes()
        );
    }

    /**
     * 토큰 쌍 사용자 정보 변환
     *
     * @param tokenPair 토큰 쌍
     * @return 로그인 결과 사용자 정보
     */
    public static LoginResultUserInfo from(LoginTokenPair tokenPair) {
        return new LoginResultUserInfo(
            tokenPair.userId(),
            tokenPair.userName(),
            tokenPair.phoneNumber(),
            tokenPair.clientIp(),
            tokenPair.organization(),
            tokenPair.attributes()
        );
    }

    /**
     * 대리점 코드 조회
     *
     * @return 대리점 코드
     */
    public String agentCode() {
        return organization.agentCode();
    }

    /**
     * 대리점명 조회
     *
     * @return 대리점명
     */
    public String agentName() {
        return organization.agentName();
    }

    /**
     * 판매점 코드 조회
     *
     * @return 판매점 코드
     */
    public String shopCode() {
        return organization.shopCode();
    }

    /**
     * 판매점명 조회
     *
     * @return 판매점명
     */
    public String shopName() {
        return organization.shopName();
    }

    /**
     * 문자열 속성 조회
     *
     * @param name 속성명
     * @return 문자열 속성 값
     */
    public String attributeAsString(String name) {
        Object value = attributes.get(name);
        return value == null ? null : String.valueOf(value);
    }

    /**
     * Boolean 속성 조회
     *
     * @param name 속성명
     * @return Boolean 속성 값
     */
    public Boolean attributeAsBoolean(String name) {
        Object value = attributes.get(name);
        if (value instanceof Boolean booleanValue) {
            return booleanValue;
        }
        return value == null ? null : Boolean.valueOf(String.valueOf(value));
    }
}
