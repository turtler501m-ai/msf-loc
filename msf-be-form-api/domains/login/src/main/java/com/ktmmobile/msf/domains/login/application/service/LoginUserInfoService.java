package com.ktmmobile.msf.domains.login.application.service;

import java.util.LinkedHashMap;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.common.data.entity.user.MsfUser;
import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.application.service.LoginUserInfoCacheService;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;
import com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.LoginUserMapper;
import com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.row.FormLoginUserInfoRow;
import com.ktmmobile.msf.domains.login.application.dto.LoginUserInfoResponse;
import com.ktmmobile.msf.domains.login.application.port.in.LoginUserInfoReader;
import com.ktmmobile.msf.domains.login.domain.code.DeviceApprovalStatusCode;
import com.ktmmobile.msf.domains.login.domain.code.LoginUserInfoAttribute;

@RequiredArgsConstructor
@Service
public class LoginUserInfoService implements LoginUserInfoReader {

    private final LoginUserMapper loginUserMapper;
    private final LoginUserInfoCacheService loginUserInfoCacheService;
    private final AgencyCacheReader agencyCacheReader;

    /**
     * 인증 사용자 정보 조회
     *
     * @param authenticatedUser 인증 사용자
     * @return 사용자 정보 응답
     */
    @Transactional(readOnly = true)
    @Override
    public LoginUserInfoResponse getUserInfo(MsfUser authenticatedUser) {
        LoginUserInfo userInfo = loginUserInfoCacheService.getOrLoad(
            UserType.FORM_USER,
            authenticatedUser.getUserId(),
            () -> findUserInfo(authenticatedUser.getUserId())
        );
        return LoginUserInfoResponse.from(userInfo);
    }

    /**
     * 사용자 ID 기준 사용자 정보 조회
     *
     * @param userId 사용자 ID
     * @return 사용자 정보
     */
    private LoginUserInfo findUserInfo(String userId) {
        FormLoginUserInfoRow row = loginUserMapper.selectFormUserInfoByUserId(userId);
        if (row == null) {
            throw new LoginException("사용자 정보를 조회할 수 없습니다.");
        }
        return new LoginUserInfo(
            row.userId(),
            row.userNm(),
            row.mobileNo(),
            UserType.FORM_USER,
            null,
            valueOrEmpty(row.agentCd()),
            organizationName(row.agentCd()),
            valueOrEmpty(row.shopCd()),
            organizationName(row.shopCd()),
            attributes(row)
        );
    }

    /**
     * 사용자 정보 부가 속성 생성
     *
     * @param row Form 사용자 정보 조회 Row
     * @return 부가 속성
     */
    private Map<String, Object> attributes(FormLoginUserInfoRow row) {
        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put(LoginUserInfoAttribute.DEVICE_AUTH_COMPLETED.key(), isApprovedDevice(row.apvSttusCd()));
        return attributes;
    }

    /**
     * 조직 ID 기준 조직명 조회
     *
     * @param organizationId 조직 ID
     * @return 조직명
     */
    private String organizationName(String organizationId) {
        if (organizationId == null || organizationId.isBlank()) {
            return "";
        }
        return agencyCacheReader.getAgency(organizationId)
            .map(AgencyCache::organizationName)
            .orElse("");
    }

    /**
     * null 문자열 빈 문자열 변환
     *
     * @param value 원본 문자열
     * @return 변환 문자열
     */
    private String valueOrEmpty(String value) {
        return value == null ? "" : value;
    }

    /**
     * null이 아닌 속성만 추가
     *
     * @param attributes 속성 Map
     * @param key 속성 키
     * @param value 속성 값
     */
    //private void putIfNotNull(Map<String, Object> attributes, String key, Object value) {
    //    if (value != null) {
    //        attributes.put(key, value);
    //    }
    //}

    /**
     * 단말 승인 상태 여부 확인
     *
     * @param code 단말 승인 상태 코드
     * @return 승인 상태 여부
     */
    private boolean isApprovedDevice(String code) {
        return DeviceApprovalStatusCode.isApproved(code);
    }
}
