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

    @Transactional(readOnly = true)
    @Override
    public LoginUserInfoResponse getUserInfo(MsfUser authenticatedUser) {
        LoginUserInfo userInfo = loginUserInfoCacheService.getOrLoad(
            UserType.FORM_USER,
            authenticatedUser.getId(),
            () -> findUserInfo(authenticatedUser.getId())
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
            attributes(row)
        );
    }

    private Map<String, Object> attributes(FormLoginUserInfoRow row) {
        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put(LoginUserInfoAttribute.DEVICE_AUTH_COMPLETED.key(), isApprovedDevice(row.apvSttusCd()));
        putIfNotNull(attributes, LoginUserInfoAttribute.AGENT_CODE.key(), row.agentCd());
        putIfNotNull(attributes, LoginUserInfoAttribute.SHOP_CODE.key(), row.shopCd());
        return attributes;
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
