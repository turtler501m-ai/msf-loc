package com.ktmmobile.msf.commons.logincore.domain.policy.completion;

import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.logincore.domain.policy.LoginPolicyLogUtils;
import com.ktmmobile.msf.commons.logincore.domain.policy.LoginPolicySelector;
import com.ktmmobile.msf.commons.logincore.domain.policy.LoginPolicyType;

/**
 * 로그인 완료 정책을 YAML whitelist 순서대로 실행하는 Composite
 */
@Slf4j
@Component
public class LoginCompletionPolicyComposite {

    private final List<SelectedCompletionPolicy> policies;

    /**
     * 로그인 완료 정책 Composite 생성
     *
     * @param policies 로그인 완료 정책 Bean Map
     * @param policySelector 로그인 정책 선택기
     */
    public LoginCompletionPolicyComposite(Map<String, LoginCompletionPolicy> policies, LoginPolicySelector policySelector) {
        List<String> missingPolicies = policySelector.missing(LoginPolicyType.COMPLETION, policies);
        if (!missingPolicies.isEmpty()) {
            log.warn("Missing LoginCompletionPolicy beans configured in login-core.policy.completion: {}", missingPolicies);
        }
        List<String> selectedNames = policySelector.selectedNames(LoginPolicyType.COMPLETION, policies);
        this.policies = selectedNames.stream()
            .map(beanName -> new SelectedCompletionPolicy(beanName, policies.get(beanName)))
            .toList();
        logSelectedPolicies(LoginPolicyLogUtils.names(policies, selectedNames));
    }

    /**
     * 로그인 완료 정책 검증
     *
     * @param context 로그인 완료 컨텍스트
     */
    public void verify(LoginCompletionContext<?> context) {
        for (SelectedCompletionPolicy selected: policies) {
            verify(selected, context);
        }
    }

    /**
     * 단일 로그인 완료 정책 검증
     *
     * @param selected 선택 정책
     * @param context 로그인 완료 컨텍스트
     */
    private void verify(SelectedCompletionPolicy selected, LoginCompletionContext<?> context) {
        boolean supported = selected.policy().supports(context);
        log.info(
            "Login completion policy evaluated. policy={}, userId={}, supported={}",
            selected.beanName(),
            context.user().userId(),
            supported
        );
        if (!supported) {
            return;
        }
        try {
            selected.policy().verify(context);
            log.info(
                "Login completion policy passed. policy={}, userId={}",
                selected.beanName(),
                context.user().userId()
            );
        } catch (RuntimeException ex) {
            log.info(
                "Login completion policy failed. policy={}, userId={}, error={}",
                selected.beanName(),
                context.user().userId(),
                ex.getMessage()
            );
            throw ex;
        }
    }

    /**
     * 선택된 로그인 완료 정책 로그 출력
     *
     * @param policyNames 정책 이름 목록 문자열
     */
    private void logSelectedPolicies(String policyNames) {
        if (LoginPolicyLogUtils.NONE.equals(policyNames)) {
            log.warn("LoginCompletionPolicy: {}", policyNames);
            return;
        }
        log.info("LoginCompletionPolicy: {}", policyNames);
    }

    private record SelectedCompletionPolicy(
        String beanName,
        LoginCompletionPolicy policy
    ) {
    }
}
