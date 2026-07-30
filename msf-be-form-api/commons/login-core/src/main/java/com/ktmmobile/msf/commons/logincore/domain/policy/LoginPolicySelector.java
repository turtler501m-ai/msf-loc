package com.ktmmobile.msf.commons.logincore.domain.policy;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;

/**
 * YAML whitelist 순서에 따라 적용할 로그인 정책 bean 선택
 */
@Component
public class LoginPolicySelector {

    private final LoginCoreProperties properties;

    /**
     * 로그인 정책 선택기 생성
     *
     * @param properties 로그인 코어 프로퍼티
     */
    public LoginPolicySelector(LoginCoreProperties properties) {
        this.properties = properties;
    }

    /**
     * 정책 Bean 활성화 여부 확인
     *
     * @param type 정책 유형
     * @param beanName Bean 이름
     * @return 활성화 여부
     */
    public boolean isEnabled(LoginPolicyType type, String beanName) {
        return enabled(type).contains(beanName);
    }

    /**
     * 활성화 정책 Bean 목록 선택
     *
     * @param type 정책 유형
     * @param beans Bean Map
     * @return 정책 Bean 목록
     */
    public <T> List<T> select(LoginPolicyType type, Map<String, T> beans) {
        return enabled(type).stream()
            .map(beans::get)
            .filter(Objects::nonNull)
            .toList();
    }

    /**
     * 활성화 정책 Bean 이름 목록 선택
     *
     * @param type 정책 유형
     * @param beans Bean Map
     * @return Bean 이름 목록
     */
    public List<String> selectedNames(LoginPolicyType type, Map<String, ?> beans) {
        return enabled(type).stream()
            .filter(beans::containsKey)
            .toList();
    }

    /**
     * 설정되었지만 등록되지 않은 정책 Bean 이름 목록 조회
     *
     * @param type 정책 유형
     * @param beans Bean Map
     * @return 누락 Bean 이름 목록
     */
    public List<String> missing(LoginPolicyType type, Map<String, ?> beans) {
        return enabled(type).stream()
            .filter(beanName -> !beans.containsKey(beanName))
            .toList();
    }

    /**
     * 활성화 정책 Bean 이름 목록 조회
     *
     * @param type 정책 유형
     * @return Bean 이름 목록
     */
    private List<String> enabled(LoginPolicyType type) {
        LoginCoreProperties.Policy policy = properties.policy();
        if (policy == null) {
            return List.of();
        }
        return switch (type) {
            case COMPLETION -> policy.completion();
            case FAILURE -> policy.failure();
            case REQUIRED_ACTION -> policy.requiredAction();
        };
    }
}
