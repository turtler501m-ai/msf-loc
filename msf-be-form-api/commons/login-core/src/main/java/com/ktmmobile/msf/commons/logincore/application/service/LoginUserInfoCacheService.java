package com.ktmmobile.msf.commons.logincore.application.service;

import java.util.Optional;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.data.type.UserType;
import com.ktmmobile.msf.commons.common.service.CacheStampedeGuard;
import com.ktmmobile.msf.commons.common.service.dto.CacheStampedeProtection;
import com.ktmmobile.msf.commons.common.service.port.CacheService;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginUserInfo;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;

/**
 * 로그인 사용자 정보 캐시와 stale 캐시 관리 서비스
 */
@RequiredArgsConstructor
@Service
public class LoginUserInfoCacheService {

    private final LoginCoreProperties properties;
    private final CacheService<LoginUserInfo> cacheService;
    private final CacheStampedeGuard cacheStampedeGuard;

    /**
     * 로그인 세션 사용자 정보 캐시 저장
     *
     * @param principal 로그인 세션 사용자
     */
    public void save(LoginSessionUser principal) {
        save(new LoginUserInfo(
            principal.userId(),
            principal.userName(),
            principal.phoneNumber(),
            principal.userType(),
            principal.clientIp(),
            principal.organization(),
            principal.attributes()
        ));
    }

    /**
     * 사용자 정보 캐시 저장
     *
     * @param userInfo 사용자 정보
     */
    public void save(LoginUserInfo userInfo) {
        LoginCoreProperties.UserInfoCache cache = properties.userInfoCache();
        // 정상 캐시와 stale 캐시를 함께 저장하여 만료 직후 DB 재조회 집중 방지
        cacheService.setValue(key(userInfo.userType(), userInfo.userId()), userInfo, cache.timeToLive());
        cacheService.setValue(staleKey(userInfo.userType(), userInfo.userId()), userInfo, cache.timeToLive().plus(cache.staleTimeToLive()));
    }

    /**
     * 사용자 정보 캐시 조회
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     * @return 사용자 정보
     */
    public Optional<LoginUserInfo> get(UserType userType, String userId) {
        return Optional.ofNullable(cacheService.getValue(key(userType, userId)));
    }

    /**
     * 사용자 정보 캐시 조회 또는 적재
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     * @param loader 사용자 정보 적재 함수
     * @return 사용자 정보
     */
    public LoginUserInfo getOrLoad(UserType userType, String userId, Supplier<LoginUserInfo> loader) {
        // 동일 사용자 정보 재적재 시 분산 락과 stale 캐시로 스탬피드 완화
        return cacheStampedeGuard.getOrLoad(
            cacheService,
            protection(userType, userId),
            loader,
            () -> new LoginException("사용자 정보 갱신 중입니다. 잠시 후 다시 시도해 주세요.")
        );
    }

    /**
     * 사용자 정보 캐시 삭제
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     */
    public void delete(UserType userType, String userId) {
        cacheService.delete(key(userType, userId));
        cacheService.delete(staleKey(userType, userId));
    }

    /**
     * 사용자 정보 캐시 스탬피드 보호 설정 생성
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     * @return 스탬피드 보호 설정
     */
    private CacheStampedeProtection protection(UserType userType, String userId) {
        LoginCoreProperties.UserInfoCache cache = properties.userInfoCache();
        return new CacheStampedeProtection(
            key(userType, userId),
            staleKey(userType, userId),
            lockKey(userType, userId),
            cache.timeToLive(),
            cache.staleTimeToLive(),
            cache.lockTimeToLive(),
            cache.lockWaitTime(),
            cache.lockRetryInterval()
        );
    }

    /**
     * 사용자 정보 캐시 키 생성
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     * @return 사용자 정보 캐시 키
     */
    private String key(UserType userType, String userId) {
        return "user-info:" + userType.getCode() + ":" + userId;
    }

    /**
     * 사용자 정보 stale 캐시 키 생성
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     * @return stale 캐시 키
     */
    private String staleKey(UserType userType, String userId) {
        return key(userType, userId) + "-stale";
    }

    /**
     * 사용자 정보 캐시 락 키 생성
     *
     * @param userType 사용자 유형
     * @param userId 사용자 ID
     * @return 캐시 락 키
     */
    private String lockKey(UserType userType, String userId) {
        return "lock:user-info:" + userType.getCode() + ":" + userId;
    }
}
