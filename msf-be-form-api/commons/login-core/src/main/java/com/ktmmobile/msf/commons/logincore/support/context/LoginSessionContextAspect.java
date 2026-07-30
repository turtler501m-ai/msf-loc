package com.ktmmobile.msf.commons.logincore.support.context;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.context.LoginContextHolder;
import com.ktmmobile.msf.commons.common.service.port.CacheService;
import com.ktmmobile.msf.commons.logincore.application.service.LoginSessionService;
import com.ktmmobile.msf.commons.logincore.domain.dto.LoginSessionUser;
import com.ktmmobile.msf.commons.logincore.support.exception.LoginException;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;

@Aspect
@Component
@RequiredArgsConstructor
public class LoginSessionContextAspect {

    private static final String LOGIN_SESSION_ID_PROPERTY = "loginSessionId";
    private static final String TOKEN_PROPERTY = "token";

    private final LoginSessionService loginSessionService;
    private final ObjectProvider<CacheService<Object>> cacheServiceProvider;

    /**
     * 로그인 세션 컨텍스트 적용
     *
     * @param joinPoint 실행 지점
     * @param loginSessionContext 로그인 세션 컨텍스트 애너테이션
     * @return 실행 결과
     */
    @Around("@annotation(loginSessionContext)")
    public Object apply(ProceedingJoinPoint joinPoint, LoginSessionContext loginSessionContext) throws Throwable {
        if (hasAuthenticatedUser()) {
            return joinPoint.proceed();
        }

        Optional<LoginSessionUser> user = findLoginSessionUser(joinPoint.getArgs());
        if (user.isEmpty()) {
            return joinPoint.proceed();
        }
        return LoginContextHolder.callWithUserId(user.get().userId(), joinPoint::proceed);
    }

    /**
     * 인증 사용자 존재 여부 확인
     *
     * @return 인증 사용자 존재 여부
     */
    private boolean hasAuthenticatedUser() {
        try {
            AuthenticationUtils.getUser();
            return true;
        } catch (Exception _) {
            return false;
        }
    }

    /**
     * 메서드 인자 배열에서 로그인 세션 사용자 조회
     *
     * @param args 메서드 인자 배열
     * @return 로그인 세션 사용자
     */
    private Optional<LoginSessionUser> findLoginSessionUser(Object[] args) {
        for (Object arg : args) {
            Optional<LoginSessionUser> user = findLoginSessionUser(arg);
            if (user.isPresent()) {
                return user;
            }
        }
        return Optional.empty();
    }

    /**
     * 메서드 인자에서 로그인 세션 사용자 조회
     *
     * @param arg 메서드 인자
     * @return 로그인 세션 사용자
     */
    private Optional<LoginSessionUser> findLoginSessionUser(Object arg) {
        return findLoginSessionId(arg)
            .flatMap(this::findLoginSessionUser);
    }

    /**
     * 로그인 세션 ID 또는 캐시 키 기준 로그인 세션 사용자 조회
     *
     * @param loginSessionIdOrCacheKey 로그인 세션 ID 또는 캐시 키
     * @return 로그인 세션 사용자
     */
    private Optional<LoginSessionUser> findLoginSessionUser(String loginSessionIdOrCacheKey) {
        Optional<LoginSessionUser> user = getPrincipal(loginSessionIdOrCacheKey);
        if (user.isPresent()) {
            return user;
        }
        return findLoginSessionIdByCache(loginSessionIdOrCacheKey)
            .flatMap(this::getPrincipal);
    }

    /**
     * 로그인 세션 ID 기준 principal 조회
     *
     * @param loginSessionId 로그인 세션 ID
     * @return 로그인 세션 사용자
     */
    private Optional<LoginSessionUser> getPrincipal(String loginSessionId) {
        try {
            return Optional.of(loginSessionService.getPrincipal(loginSessionId));
        } catch (LoginException _) {
            return Optional.empty();
        }
    }

    /**
     * 캐시 키 기준 로그인 세션 ID 조회
     *
     * @param cacheKey 캐시 키
     * @return 로그인 세션 ID
     */
    private Optional<String> findLoginSessionIdByCache(String cacheKey) {
        CacheService<Object> cacheService = cacheServiceProvider.getIfAvailable();
        if (cacheService == null) {
            return Optional.empty();
        }

        Object value = cacheService.getValue(cacheKey);
        return Optional.ofNullable(readProperty(value, TOKEN_PROPERTY))
            .filter(StringUtils::hasText);
    }

    /**
     * 인자에서 로그인 세션 ID 추출
     *
     * @param arg 메서드 인자
     * @return 로그인 세션 ID
     */
    private Optional<String> findLoginSessionId(Object arg) {
        if (arg instanceof String value) {
            return Optional.of(value)
                .filter(StringUtils::hasText);
        }
        if (arg == null) {
            return Optional.empty();
        }
        String value = readProperty(arg, LOGIN_SESSION_ID_PROPERTY);
        if (StringUtils.hasText(value)) {
            return Optional.of(value);
        }
        return Optional.ofNullable(readProperty(arg, TOKEN_PROPERTY))
            .filter(StringUtils::hasText);
    }

    /**
     * 객체 속성 문자열 조회
     *
     * @param arg 대상 객체
     * @param propertyName 속성명
     * @return 속성 값
     */
    private String readProperty(Object arg, String propertyName) {
        if (arg == null) {
            return null;
        }
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(arg);
        if (!beanWrapper.isReadableProperty(propertyName)) {
            return null;
        }
        Object value = beanWrapper.getPropertyValue(propertyName);
        return value == null ? null : String.valueOf(value);
    }
}
