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

    private Map<String, Object> attributes(FormLoginUserInfoRow row) {
        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put(LoginUserInfoAttribute.DEVICE_AUTH_COMPLETED.key(), isApprovedDevice(row.apvSttusCd()));
        return attributes;
    }

    private String organizationName(String organizationId) {
        if (organizationId == null || organizationId.isBlank()) {
            return "";
        }
        return agencyCacheReader.getAgency(organizationId)
            .map(AgencyCache::organizationName)
            .orElse("");
    }

    private String valueOrEmpty(String value) {
        return value == null ? "" : value;
    }

    private void putIfNotNull(Map<String, Object> attributes, String key, Object value) {
        if (value != null) {
            attributes.put(key, value);
        }
    }

    private boolean isApprovedDevice(String code) {
        return DeviceApprovalStatusCode.isApproved(code);
    }
}
