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

    private boolean hasAuthenticatedUser() {
        try {
            AuthenticationUtils.getUser();
            return true;
        } catch (Exception _) {
            return false;
        }
    }

    private Optional<LoginSessionUser> findLoginSessionUser(Object[] args) {
        for (Object arg : args) {
            Optional<LoginSessionUser> user = findLoginSessionUser(arg);
            if (user.isPresent()) {
                return user;
            }
        }
        return Optional.empty();
    }

    private Optional<LoginSessionUser> findLoginSessionUser(Object arg) {
        return findLoginSessionId(arg)
            .flatMap(this::findLoginSessionUser);
    }

    private Optional<LoginSessionUser> findLoginSessionUser(String loginSessionIdOrCacheKey) {
        Optional<LoginSessionUser> user = getPrincipal(loginSessionIdOrCacheKey);
        if (user.isPresent()) {
            return user;
        }
        return findLoginSessionIdByCache(loginSessionIdOrCacheKey)
            .flatMap(this::getPrincipal);
    }

    private Optional<LoginSessionUser> getPrincipal(String loginSessionId) {
        try {
            return Optional.of(loginSessionService.getPrincipal(loginSessionId));
        } catch (LoginException _) {
            return Optional.empty();
        }
    }

    private Optional<String> findLoginSessionIdByCache(String cacheKey) {
        CacheService<Object> cacheService = cacheServiceProvider.getIfAvailable();
        if (cacheService == null) {
            return Optional.empty();
        }

        Object value = cacheService.getValue(cacheKey);
        return Optional.ofNullable(readProperty(value, TOKEN_PROPERTY))
            .filter(StringUtils::hasText);
    }

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
