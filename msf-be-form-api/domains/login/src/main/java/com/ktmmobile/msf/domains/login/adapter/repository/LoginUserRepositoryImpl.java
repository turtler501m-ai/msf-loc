package com.ktmmobile.msf.domains.login.adapter.repository;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginUserFinder;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;
import com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.LoginUserMapper;
import com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.row.FormLoginUserInfoRow;
import com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.row.FormLoginUserRow;
import com.ktmmobile.msf.domains.login.application.dto.LoginCredential;
import com.ktmmobile.msf.domains.login.domain.code.DeviceApprovalStatusCode;
import com.ktmmobile.msf.domains.login.domain.code.LoginUserInfoAttribute;
import com.ktmmobile.msf.domains.login.domain.code.UserStatusCode;

@RequiredArgsConstructor
@Repository
public class LoginUserRepositoryImpl implements LoginUserFinder<LoginCredential> {

    private final LoginUserMapper loginUserMapper;

    @Override
    public Optional<LoginUser> findByCredential(LoginCredential credential) {
        if (credential.isDeviceAuth()) {
            return Optional.ofNullable(loginUserMapper.selectFormByDeviceUuid(credential.deviceUuid()))
                .map(this::toFormLoginUser);
        }
        return Optional.ofNullable(loginUserMapper.selectFormByUserId(credential.userId()))
            .map(this::toFormLoginUser);
    }

    @Override
    public Optional<LoginUserInfo> findUserInfo(LoginUser user, LoginCredential credential) {
        return Optional.ofNullable(loginUserMapper.selectFormUserInfoByUserIdAndDeviceUuid(user.userId(), credential.deviceUuid()))
            .map(row -> toLoginUserInfo(row, isApprovedDevice(row.apvSttusCd())));
    }

    @Override
    public void recordLoginSuccess(LoginUser user, LoginCredential credential) {
        if (credential.isDeviceAuth()) {
            loginUserMapper.updateFormDeviceLoginSuccess(credential.deviceUuid());
            return;
        }
        loginUserMapper.updateFormLoginSuccess(user.userId());
    }

    @Override
    public void recordLoginFailure(LoginUser user, LoginCredential credential, boolean shouldLock) {
        if (shouldLock) {
            loginUserMapper.insertFormUserHistory(user.userId());
        }
        loginUserMapper.updateFormLoginFailure(user.userId(), shouldLock ? UserStatusCode.LOCKED.getCode() : null);
    }

    private LoginUser toFormLoginUser(FormLoginUserRow row) {
        return new LoginUser(
            row.userId(),
            null,
            null,
            row.pwd(),
            isActive(row.userSttusCd()) && !isYes(row.accessLimitYn()),
            nextLoginFailCount(row.loginChkCnt()),
            passwordChangeRequired(row.pwdChgYn(), row.pwdChgDt()),
            null
        );
    }

    private LoginUserInfo toLoginUserInfo(FormLoginUserInfoRow row, boolean deviceAuthCompleted) {
        return new LoginUserInfo(
            row.userId(),
            row.userNm(),
            row.mobileNo(),
            UserType.FORM_USER,
            attributes(row, deviceAuthCompleted)
        );
    }

    private Map<String, Object> attributes(FormLoginUserInfoRow row, boolean deviceAuthCompleted) {
        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put(LoginUserInfoAttribute.DEVICE_AUTH_COMPLETED.key(), deviceAuthCompleted);
        putIfNotNull(attributes, LoginUserInfoAttribute.AGENT_CODE.key(), row.agentCd());
        putIfNotNull(attributes, LoginUserInfoAttribute.SHOP_CODE.key(), row.shopCd());
        return attributes;
    }

    private void putIfNotNull(Map<String, Object> attributes, String key, Object value) {
        if (value != null) {
            attributes.put(key, value);
        }
    }

    private int nextLoginFailCount(Integer loginFailCount) {
        return (loginFailCount == null ? 0 : loginFailCount) + 1;
    }

    private boolean isActive(String code) {
        return UserStatusCode.isActive(code);
    }

    private boolean isApprovedDevice(String code) {
        return DeviceApprovalStatusCode.isApproved(code);
    }

    private boolean isYes(String value) {
        return "Y".equals(value);
    }

    private boolean passwordChangeRequired(String passwordChangeYn, LocalDateTime passwordChangedAt) {
        return isYes(passwordChangeYn)
            || passwordChangedAt == null
            || passwordChangedAt.isBefore(LocalDateTime.now().minusMonths(3));
    }
}
