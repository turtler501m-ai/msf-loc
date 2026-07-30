package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.util.LinkedHashMap;
import java.util.Map;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;

/**
 * 로그인 완료 후 API 인증에서 재사용할 사용자 상세 정보
 */
public record LoginUserInfo(
    String userId,
    String userName,
    String phoneNumber,
    UserType userType,
    String clientIp,
    LoginOrganization organization,
    Map<String, Object> attributes
) {

    public LoginUserInfo {
        organization = organization == null ? LoginOrganization.empty() : organization;
        attributes = attributes == null ? new LinkedHashMap<>() : new LinkedHashMap<>(attributes);
    }

    /**
     * 조직 정보 없는 사용자 정보 생성
     *
     * @param userId 사용자 ID
     * @param userName 사용자명
     * @param phoneNumber 휴대폰번호
     * @param userType 사용자 유형
     * @param clientIp 클라이언트 IP
     * @param attributes 부가 속성
     */
    public LoginUserInfo(
        String userId,
        String userName,
        String phoneNumber,
        UserType userType,
        String clientIp,
        Map<String, Object> attributes
    ) {
        this(userId, userName, phoneNumber, userType, clientIp, LoginOrganization.empty(), attributes);
    }

    /**
     * 조직 코드 기반 사용자 정보 생성
     *
     * @param userId 사용자 ID
     * @param userName 사용자명
     * @param phoneNumber 휴대폰번호
     * @param userType 사용자 유형
     * @param clientIp 클라이언트 IP
     * @param agentCode 대리점 코드
     * @param agentName 대리점명
     * @param shopCode 판매점 코드
     * @param shopName 판매점명
     * @param attributes 부가 속성
     */
    public LoginUserInfo(
        String userId,
        String userName,
        String phoneNumber,
        UserType userType,
        String clientIp,
        String agentCode,
        String agentName,
        String shopCode,
        String shopName,
        Map<String, Object> attributes
    ) {
        this(userId, userName, phoneNumber, userType, clientIp, new LoginOrganization(agentCode, agentName, shopCode, shopName), attributes);
    }

    /**
     * 로그인 사용자 기준 기본 사용자 정보 생성
     *
     * @param user 로그인 사용자
     * @param userType 사용자 유형
     * @return 사용자 정보
     */
    public static LoginUserInfo of(LoginUser user, UserType userType) {
        return new LoginUserInfo(
            user.userId(),
            user.userName(),
            user.phoneNumber(),
            userType,
            null,
            LoginOrganization.empty(),
            Map.of()
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
