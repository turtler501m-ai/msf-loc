package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.commons.common.data.type.UserType;

/**
 * 로그인 세션과 인증 사용자 캐시에 저장되는 사용자 정보
 */
public record LoginSessionUser(
    String userId,
    UserType userType,
    String userName,
    String phoneNumber,
    String clientIp,
    LoginOrganization organization,
    Map<String, Object> attributes,
    List<LoginRequiredAction> requiredActions
) {

    /**
     * 기본 로그인 세션 사용자 생성
     *
     * @param userId 사용자 ID
     * @param userName 사용자명
     * @param userType 사용자 유형
     * @param phoneNumber 휴대폰번호
     */
    public LoginSessionUser(String userId, String userName, UserType userType, String phoneNumber) {
        this(userId, userType, userName, phoneNumber, null, LoginOrganization.empty(), Map.of(), List.of());
    }

    /**
     * 필수 조치 포함 로그인 세션 사용자 생성
     *
     * @param userId 사용자 ID
     * @param userName 사용자명
     * @param userType 사용자 유형
     * @param phoneNumber 휴대폰번호
     * @param requiredActions 필수 조치 목록
     */
    public LoginSessionUser(
        String userId,
        String userName,
        UserType userType,
        String phoneNumber,
        List<LoginRequiredAction> requiredActions
    ) {
        this(userId, userType, userName, phoneNumber, null, LoginOrganization.empty(), Map.of(), requiredActions);
    }

    /**
     * 부가 속성 포함 로그인 세션 사용자 생성
     *
     * @param userId 사용자 ID
     * @param userType 사용자 유형
     * @param userName 사용자명
     * @param phoneNumber 휴대폰번호
     * @param clientIp 클라이언트 IP
     * @param attributes 부가 속성
     * @param requiredActions 필수 조치 목록
     */
    public LoginSessionUser(
        String userId,
        UserType userType,
        String userName,
        String phoneNumber,
        String clientIp,
        Map<String, Object> attributes,
        List<LoginRequiredAction> requiredActions
    ) {
        this(userId, userType, userName, phoneNumber, clientIp, LoginOrganization.empty(), attributes, requiredActions);
    }

    /**
     * 조직 코드 기반 로그인 세션 사용자 생성
     *
     * @param userId 사용자 ID
     * @param userType 사용자 유형
     * @param userName 사용자명
     * @param phoneNumber 휴대폰번호
     * @param clientIp 클라이언트 IP
     * @param agentCode 대리점 코드
     * @param agentName 대리점명
     * @param shopCode 판매점 코드
     * @param shopName 판매점명
     * @param attributes 부가 속성
     * @param requiredActions 필수 조치 목록
     */
    public LoginSessionUser(
        String userId,
        UserType userType,
        String userName,
        String phoneNumber,
        String clientIp,
        String agentCode,
        String agentName,
        String shopCode,
        String shopName,
        Map<String, Object> attributes,
        List<LoginRequiredAction> requiredActions
    ) {
        this(userId, userType, userName, phoneNumber, clientIp, new LoginOrganization(agentCode, agentName, shopCode, shopName), attributes, requiredActions);
    }

    public LoginSessionUser {
        organization = organization == null ? LoginOrganization.empty() : organization;
        attributes = attributes == null ? new LinkedHashMap<>() : new LinkedHashMap<>(attributes);
        requiredActions = requiredActions == null ? new ArrayList<>() : new ArrayList<>(requiredActions);
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
     * 조직 레벨 코드 조회
     *
     * @return 조직 레벨 코드
     */
    public String levelCode() {
        return organization.levelCode();
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
