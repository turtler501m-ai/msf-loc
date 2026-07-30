package com.ktmmobile.msf.domains.login.adapter.repository;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginAuthenticationRecorder;
import com.ktmmobile.msf.commons.logincore.application.port.out.LoginUserFinder;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.domain.entity.LoginUser;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;
import com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.LoginUserMapper;
import com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.row.FormLoginUserInfoRow;
import com.ktmmobile.msf.domains.login.adapter.repository.mybatis.smartform.mapper.row.FormLoginUserRow;
import com.ktmmobile.msf.domains.login.application.dto.LoginCredential;
import com.ktmmobile.msf.domains.login.domain.code.DeviceApprovalStatusCode;
import com.ktmmobile.msf.domains.login.domain.code.LoginUserInfoAttribute;
import com.ktmmobile.msf.domains.login.domain.code.UserStatusCode;

@RequiredArgsConstructor
@Repository
public class LoginUserRepositoryImpl implements LoginUserFinder<LoginCredential>, LoginAuthenticationRecorder<LoginCredential> {

    private final LoginUserMapper loginUserMapper;
    private final AgencyCacheReader agencyCacheReader;

    /**
     * 로그인 인증 대상 사용자 조회
     *
     * @param credential 로그인 인증 정보
     * @return 로그인 사용자
     */
    @Override
    public Optional<LoginUser> findByCredential(LoginCredential credential) {
        if (credential.isDeviceAuth()) {
            return Optional.ofNullable(loginUserMapper.selectFormByUserIdAndDeviceUuid(credential.userId(), credential.deviceUuid()))
                .map(this::toFormLoginUser);
        }
        return Optional.ofNullable(loginUserMapper.selectFormByUserId(credential.userId()))
            .map(this::toFormLoginUser);
    }

    /**
     * ID/PW 인증 직후 사용자 상세 정보 조회
     *
     * @param user 로그인 사용자
     * @param credential 로그인 인증 정보
     * @return 사용자 상세 정보
     */
    @Override
    public Optional<LoginUserInfo> findUserInfo(LoginUser user, LoginCredential credential) {
        return Optional.ofNullable(loginUserMapper.selectFormUserInfoByUserIdAndDeviceUuid(user.userId(), credential.deviceUuid()))
            .map(row -> toLoginUserInfo(row, isApprovedDevice(row.apvSttusCd()), credential.deviceUuid()));
    }

    /**
     * 로그인 세션 사용자 기준 사용자 상세 정보 조회
     *
     * @param sessionUser 로그인 세션 사용자
     * @return 사용자 상세 정보
     */
    @Override
    public Optional<LoginUserInfo> findUserInfo(LoginSessionUser sessionUser) {
        return Optional.ofNullable(loginUserMapper.selectFormUserInfoByUserId(sessionUser.userId()))
            .map(row -> toLoginUserInfo(row, isApprovedDevice(row.apvSttusCd()), null));
    }

    /**
     * 인증 완료 사용자 추가 검증
     *
     * @param user 로그인 사용자
     * @param credential 로그인 인증 정보
     */
    @Override
    public void verifyAuthenticatedUser(LoginUser user, LoginCredential credential) {
        verifyApprovedDeviceOwner(credential);
    }

    /**
     * Access Token 발급 성공 후 로그인 성공 기록
     *
     * @param user 로그인 세션 사용자
     */
    @Override
    public void recordAccessTokenIssueSuccess(LoginSessionUser user) {
        loginUserMapper.updateFormLoginSuccess(user.userId());
        String deviceUuid = user.attributeAsString(LoginUserInfoAttribute.DEVICE_UUID.key());
        if (StringUtils.hasText(deviceUuid)) {
            loginUserMapper.updateFormDeviceLoginSuccess(user.userId(), deviceUuid);
        }
    }

    /**
     * 로그인 실패 기록
     *
     * @param user 로그인 사용자
     * @param credential 로그인 인증 정보
     * @param shouldLock 계정 잠금 여부
     */
    @Override
    public void recordLoginFailure(LoginUser user, LoginCredential credential, boolean shouldLock) {
        if (shouldLock) {
            loginUserMapper.insertFormUserHistory(user.userId());
        }
        loginUserMapper.updateFormLoginFailure(user.userId(), shouldLock ? UserStatusCode.LOCKED.getCode() : null);
    }

    /**
     * Form 로그인 사용자 도메인 변환
     *
     * @param row Form 로그인 사용자 조회 Row
     * @return 로그인 사용자
     */
    private LoginUser toFormLoginUser(FormLoginUserRow row) {
        return new LoginUser(
            row.userId(),
            null,
            null,
            row.pwd(),
            isActive(row.userSttusCd()) && !isYes(row.accessLimitYn()),
            nextLoginFailCount(row.loginChkCnt()),
            passwordChangeRequired(row.pwdChgDt()),
            row.ip()
        );
    }

    /**
     * Form 사용자 상세 정보 변환
     *
     * @param row Form 사용자 정보 조회 Row
     * @param deviceAuthCompleted 단말 인증 완료 여부
     * @param deviceUuid 단말 UUID
     * @return 사용자 상세 정보
     */
    private LoginUserInfo toLoginUserInfo(FormLoginUserInfoRow row, boolean deviceAuthCompleted, String deviceUuid) {
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
            attributes(deviceAuthCompleted, deviceUuid)
        );
    }

    /**
     * 사용자 정보 부가 속성 생성
     *
     * @param deviceAuthCompleted 단말 인증 완료 여부
     * @param deviceUuid 단말 UUID
     * @return 부가 속성
     */
    private Map<String, Object> attributes(boolean deviceAuthCompleted, String deviceUuid) {
        Map<String, Object> attributes = new LinkedHashMap<>();
        attributes.put(LoginUserInfoAttribute.DEVICE_AUTH_COMPLETED.key(), deviceAuthCompleted);
        putIfNotNull(attributes, LoginUserInfoAttribute.DEVICE_UUID.key(), deviceUuid);
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
    private void putIfNotNull(Map<String, Object> attributes, String key, Object value) {
        if (value != null) {
            attributes.put(key, value);
        }
    }

    /**
     * 승인 단말 소유 사용자 검증
     *
     * @param credential 로그인 인증 정보
     */
    private void verifyApprovedDeviceOwner(LoginCredential credential) {
        if (!StringUtils.hasText(credential.deviceUuid())) {
            return;
        }
        List<String> registeredUserIds = loginUserMapper.selectApprovedDeviceOwnerUserIds(credential.deviceUuid());
        boolean usedByOtherUser = registeredUserIds.stream()
            .anyMatch(registeredUserId -> !Objects.equals(registeredUserId, credential.userId()));
        if (usedByOtherUser) {
            throw new LoginException("타 계정이 사용 중인 단말입니다.");
        }
    }

    /**
     * 다음 로그인 실패 횟수 계산
     *
     * @param loginFailCount 현재 실패 횟수
     * @return 다음 실패 횟수
     */
    private int nextLoginFailCount(Integer loginFailCount) {
        return (loginFailCount == null ? 0 : loginFailCount) + 1;
    }

    /**
     * 사용자 활성 상태 여부 확인
     *
     * @param code 사용자 상태 코드
     * @return 활성 상태 여부
     */
    private boolean isActive(String code) {
        return UserStatusCode.isActive(code);
    }

    /**
     * 단말 승인 상태 여부 확인
     *
     * @param code 단말 승인 상태 코드
     * @return 승인 상태 여부
     */
    private boolean isApprovedDevice(String code) {
        return DeviceApprovalStatusCode.isApproved(code);
    }

    /**
     * Y 값 여부 확인
     *
     * @param value 확인 값
     * @return Y 여부
     */
    private boolean isYes(String value) {
        return "Y".equals(value);
    }

    /**
     * 비밀번호 변경 필요 여부 확인
     *
     * @param passwordChangedAt 비밀번호 변경 일시
     * @return 비밀번호 변경 필요 여부
     */
    private boolean passwordChangeRequired(LocalDateTime passwordChangedAt) {
        return passwordChangedAt == null
            || passwordChangedAt.isBefore(LocalDateTime.now().minusMonths(3));
    }
}
