package com.ktmmobile.msf.commons.logincore.domain.policy;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.support.properties.LoginCoreProperties;

@Component
public class LoginPolicySelector {

    private final LoginCoreProperties properties;

    public LoginPolicySelector(LoginCoreProperties properties) {
        this.properties = properties;
    }

    public boolean isEnabled(LoginPolicyType type, String beanName) {
        return enabled(type).contains(beanName);
    }

    public <T> List<T> select(LoginPolicyType type, Map<String, T> beans) {
        return enabled(type).stream()
            .map(beans::get)
            .filter(Objects::nonNull)
            .toList();
    }

    public List<String> selectedNames(LoginPolicyType type, Map<String, ?> beans) {
        return enabled(type).stream()
            .filter(beans::containsKey)
            .toList();
    }

    public List<String> missing(LoginPolicyType type, Map<String, ?> beans) {
        return enabled(type).stream()
            .filter(beanName -> !beans.containsKey(beanName))
            .toList();
    }

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
