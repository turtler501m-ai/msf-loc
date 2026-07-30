package com.ktmmobile.msf.commons.logincore.domain.policy;

import java.util.List;
import java.util.Map;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.aop.support.AopUtils;

@SuppressWarnings("PMD.UnusedPrivateMethod")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class LoginPolicyLogUtils {

    public static final String NONE = "(none)";

    /**
     * 정책 Bean 이름 로그 문자열 생성
     *
     * @param policies 정책 Bean Map
     * @param beanNames Bean 이름 목록
     * @return 로그 문자열
     */
    public static String names(Map<String, ?> policies, List<String> beanNames) {
        if (beanNames.isEmpty()) {
            return NONE;
        }
        return beanNames.stream()
            .map(beanName -> name(beanName, policies.get(beanName)))
            .reduce((left, right) -> left + ", " + right)
            .orElse(NONE);
    }

    /**
     * 정책 Bean 이름 문자열 생성
     *
     * @param beanName Bean 이름
     * @param policy 정책 Bean
     * @return 정책 Bean 이름 문자열
     */
    private static String name(String beanName, Object policy) {
        if (policy == null) {
            return beanName + "(missing)";
        }
        return beanName;
    }

    /**
     * 정책 객체 클래스명 조회
     *
     * @param policy 정책 객체
     * @return 정책 클래스명
     */
    private static String name(Object policy) {
        Class<?> targetClass = AopUtils.getTargetClass(policy);
        return targetClass.getSimpleName();
    }
}
