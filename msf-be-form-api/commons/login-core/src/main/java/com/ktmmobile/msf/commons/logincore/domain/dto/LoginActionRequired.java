package com.ktmmobile.msf.commons.logincore.domain.dto;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.commons.common.data.type.UserType;

public record LoginActionRequired(
    String loginSessionId,
    String userId,
    UserType userType,
    String userName,
    String phoneNumber,
    String clientIp,
    LoginOrganization organization,
    Map<String, Object> attributes,
    List<LoginRequiredAction> requiredActions
) implements LoginResult {

    /**
     * 조직 코드 기반 필수 조치 결과 생성
     *
     * @param loginSessionId 로그인 세션 ID
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
    public LoginActionRequired(
        String loginSessionId,
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
        this(loginSessionId, userId, userType, userName, phoneNumber, clientIp,
            new LoginOrganization(agentCode, agentName, shopCode, shopName), attributes, requiredActions);
    }

    public LoginActionRequired {
        organization = organization == null ? LoginOrganization.empty() : organization;
        attributes = attributes == null ? new LinkedHashMap<>() : new LinkedHashMap<>(attributes);
        requiredActions = requiredActions == null ? new ArrayList<>() : new ArrayList<>(requiredActions);
    }
}
