package com.ktmmobile.msf.commons.logincore.domain.policy;

import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.aop.support.AopUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoginPolicyLogUtils {

    public static final String NONE = "(none)";

    public static String names(Map<String, ?> policies, List<String> beanNames) {
        if (beanNames.isEmpty()) {
            return NONE;
        }
        return beanNames.stream()
            .map(beanName -> name(beanName, policies.get(beanName)))
            .reduce((left, right) -> left + ", " + right)
            .orElse(NONE);
    }

    private static String name(String beanName, Object policy) {
        if (policy == null) {
            return beanName + "(missing)";
        }
        return beanName;
    }

    private static String name(Object policy) {
        Class<?> targetClass = AopUtils.getTargetClass(policy);
        return targetClass.getSimpleName();
    }
}
